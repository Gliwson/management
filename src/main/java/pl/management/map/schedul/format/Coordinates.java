package pl.management.map.schedul.format;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Coordinates {

    private Pattern SEARCH_COORDINATES_IN_URL;
    private Pattern SEARCH_COORDINATES_IN_URL_2;
    private double lat = 0;
    private double lon = 0;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setSEARCH_COORDINATES_IN_URL(Pattern SEARCH_COORDINATES_IN_URL) {
        this.SEARCH_COORDINATES_IN_URL = SEARCH_COORDINATES_IN_URL;
    }

    public void setSEARCH_COORDINATES_IN_URL_2(Pattern SEARCH_COORDINATES_IN_URL_2) {
        this.SEARCH_COORDINATES_IN_URL_2 = SEARCH_COORDINATES_IN_URL_2;
    }

    public boolean coordinates(String sURL) {
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
