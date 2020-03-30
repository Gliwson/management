package pl.management.map.service.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.repository.DataRepo;
import pl.management.map.service.dto.Point;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ImportWithSheetsGoogle {

    public static final Pattern MY_PATTERN = Pattern.compile("(\\d{2}+[.]+\\d{7}+[,]+\\d{2}+[.]+\\d{7})");
    public static final Pattern searchCoordinatesInURL = Pattern.compile("\\d{2}+[.]+\\d{5,}+[,]+\\d{2}+[.]+\\d{5,}");
    public static final String URL_Spred_SHEETS = "https://docs.google.com/spreadsheets/d/e/2PACX-1vRyg5iEdeVVNao1JCoMeSQOCxtDj3-4HsxsVF56JajzmPJ8WSruk-MFJucSiBFf2rwahEXZwGQpAgfn/pub?gid=1660720553&single=true&output=csv";
    private DataRepo dataRepo;
    private double lat = 0;
    private double lon = 0;

    public ImportWithSheetsGoogle(DataRepo dataRepo) {
        this.dataRepo = dataRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() throws IOException, BlankSheetException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String forObject = restTemplate.getForObject(URL_Spred_SHEETS, String.class);
        StringReader stringReader = new StringReader(forObject);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        if (parser.iterator().next().get("location_href").isEmpty()) {
            throw new BlankSheetException("Location Href in google Sheet is empty!");
        }
        for (CSVRecord strings : parser) {
            String sURL = strings.get("location_href");
            String colorsName = strings.get("colors_name");
            String colorsComments = strings.get("colors_comments");
            String locationHref = strings.get("location_href");
            String comments = strings.get("comments");
            String name = strings.get("name");
            String dyskHref = strings.get("dysk_href");
            String id = strings.get("id");

            if (coordinates(sURL)) continue;

            dataRepo.addPoint(new Point(lat, lon, id, name, comments, locationHref, colorsName, colorsComments, dyskHref));

        }
        System.out.println("END");
    }


    private boolean coordinates(String sURL) {
        if (sURL.equals("")) {
            return true;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = restTemplate.headForHeaders(sURL);
            String nameUrl = Objects.requireNonNull(httpHeaders.getLocation()).toString();
            Matcher m = searchCoordinatesInURL.matcher(nameUrl);
            if (m.find()) {
                String[] splitUrlOnCoordinates = nameUrl.split(".*@|,\\d+[a-z].*.data.*");
                String coordinates = cleanArray(splitUrlOnCoordinates)[0];
                saveCoordinates(coordinates);

            } else {
                coordinatesWithGetHtml(nameUrl);
            }
        } catch (HttpClientErrorException | IllegalArgumentException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private void saveCoordinates(String coordinates) throws ArrayIndexOutOfBoundsException {
        String[] split1 = coordinates.split(",");
        lat = Double.parseDouble(split1[0]);
        lon = Double.parseDouble(split1[1]);
    }

    public static String[] cleanArray(String[] array) {
        return Arrays.stream(array).filter(Objects::nonNull).filter(x -> !x.equals("")).toArray(String[]::new);
    }

    private void coordinatesWithGetHtml(String sURL) {
        if (sURL.equals("")) {
            return;
        }
        String htmlString;
        try {
            RestTemplate restTemplate = new RestTemplate();
            htmlString = restTemplate.getForObject(sURL, String.class);
        } catch (HttpClientErrorException e) {
            return;
        }
        String replace = htmlString.replace("[" + '\\' + '"', "\n");
        String replaceEnd = replace.replace("\\" + '"' + "]", " ");
        String replaceEnd2 = replaceEnd.replace(" \\" + "n", "\n");
        Matcher m = MY_PATTERN.matcher(replaceEnd2);
        try {
            String[] split = new String[0];

            while (m.find()) {
                String s = m.group(1);
                split = s.split("\n");
            }
            String coordinates = split[0];
            saveCoordinates(coordinates);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}

