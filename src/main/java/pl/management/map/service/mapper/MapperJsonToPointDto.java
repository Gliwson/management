package pl.management.map.service.mapper;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.management.map.repository.DataRepoCSV;
import pl.management.map.service.dto.PointDTO;
import pl.management.map.service.dto.RowDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MapperJsonToPointDto {
    private final DataRepoCSV dataRepoCSV;

    public static final Pattern SEARCH_COORDINATES_IN_URL_2 = Pattern.compile("(\\d{2}+[.]+\\d{7}+[,]+\\d{2}+[.]+\\d{7})");
    public static final Pattern SEARCH_COORDINATES_IN_URL = Pattern.compile("\\d{2}+[.]+\\d{5,}+[,]+\\d{2}+[.]+\\d{5,}");
    private final List<PointDTO> pointDTOS = new ArrayList<>();
    private double lat = 0;
    private double lon = 0;

    public MapperJsonToPointDto(DataRepoCSV dataRepoCSV) {
        this.dataRepoCSV = dataRepoCSV;
    }

    public List<PointDTO> map(List<RowDTO> json) {
        if (!dataRepoCSV.getPointList().isEmpty()) {
            dataRepoCSV.clear();
        }

        if (json == null) {
            throw new IllegalStateException("json is null");
        }

        for (RowDTO e : json) {
            if (coordinates(e.getUrlLocation())) continue;
            PointDTO pointDTO = PointDTO.builder()
                    .id(e.getId().toString())
                    .name(e.getName())
                    .colorsComments(e.getCommentsColor())
                    .colorsName(e.getNameColor())
                    .locationHref(e.getUrlLocation())
                    .dyskHref(e.getUrlDysk())
                    .x(lat)
                    .y(lon)
                    .build();
            pointDTOS.add(pointDTO);
            dataRepoCSV.addPoint(new PointDTO(lat, lon, e.getId().toString(), e.getName(),
                    e.getComments(), e.getUrlLocation(), e.getNameColor(), e.getCommentsColor(), e.getUrlDysk()));
        }
        return pointDTOS;
    }

    private boolean coordinates(String sURL) {
        if (sURL.equals("")) {
            return true;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = restTemplate.headForHeaders(sURL);
            String nameUrl = Objects.requireNonNull(httpHeaders.getLocation()).toString();
            Matcher m = SEARCH_COORDINATES_IN_URL.matcher(nameUrl);
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
        if (htmlString == null) {
            throw new IllegalArgumentException("Content download failed");
        }
        String replace = htmlString.replace("[" + '\\' + '"', "\n");
        String replace_2 = replace.replace("\\" + '"' + "]", " ");
        String replaceEnd = replace_2.replace(" \\" + "n", "\n");
        Matcher m = SEARCH_COORDINATES_IN_URL_2.matcher(replaceEnd);
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
