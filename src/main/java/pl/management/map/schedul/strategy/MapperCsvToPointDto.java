package pl.management.map.schedul.strategy;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.management.domainmodel.PointDTO;
import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.schedul.model.Format;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MapperCsvToPointDto implements TypeOfImport {

    public static final Pattern SEARCH_COORDINATES_IN_URL_2 = Pattern.compile("(\\d{2}+[.]+\\d{7}+[,]+\\d{2}+[.]+\\d{7})");
    public static final Pattern SEARCH_COORDINATES_IN_URL = Pattern.compile("\\d{2}+[.]+\\d{5,}+[,]+\\d{2}+[.]+\\d{5,}");

    @Override
    public List<PointDTO> map(String url) throws BlankSheetException, IOException {
        List<PointDTO> pointDTOS = new ArrayList<>();
        Coordinates c = new Coordinates();
        RestTemplate restTemplate = new RestTemplate();

        c.setSEARCH_COORDINATES_IN_URL(SEARCH_COORDINATES_IN_URL);
        c.setSEARCH_COORDINATES_IN_URL_2(SEARCH_COORDINATES_IN_URL_2);

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String resultSheets = restTemplate.getForObject(url, String.class);
        if (resultSheets == null) {
            throw new BlankSheetException("Result Sheets is null");
        }
        StringReader stringReader = new StringReader(resultSheets);
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
            //TODO replace the PointDto with Task
            if (c.coordinates(sURL)) continue;
            PointDTO pointDTO = PointDTO.builder()
                    .id(id)
                    .name(name)
                    .colorsComments(colorsComments)
                    .colorsName(colorsName)
                    .locationHref(locationHref)
                    .dyskHref(dyskHref)
                    .comments(comments)
                    .x(c.getLat())
                    .y(c.getLon())
                    .build();
            pointDTOS.add(pointDTO);
        }
        return pointDTOS;
    }

    @Override
    public Format getStrategyName() {
        return Format.CSV;
    }
}

