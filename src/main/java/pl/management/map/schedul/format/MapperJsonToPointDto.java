package pl.management.map.schedul.format;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.management.map.schedul.dto.ListJsonDTO;
import pl.management.map.schedul.dto.RowDTO;
import pl.management.map.service.dto.PointDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
@Service
public class MapperJsonToPointDto implements TypeOfImport {


    public static final Pattern SEARCH_COORDINATES_IN_URL_2 = Pattern.compile("(\\d{2}+[.]+\\d{7}+[,]+\\d{2}+[.]+\\d{7})");
    public static final Pattern SEARCH_COORDINATES_IN_URL = Pattern.compile("\\d{2}+[.]+\\d{5,}+[,]+\\d{2}+[.]+\\d{5,}");

    @Value("${urlJson}")
    private String urlJson;

    private final List<PointDTO> pointDTOS = new ArrayList<>();

    public List<PointDTO> map() {
        Coordinates c = new Coordinates();
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();

        log.info(urlJson);

        String stringJson = restTemplate.getForObject(urlJson, String.class);
        ListJsonDTO user = gson.fromJson(stringJson, ListJsonDTO.class);
        List<RowDTO> jsonList = user.getUser();
        log.info("Execute");

        c.setSEARCH_COORDINATES_IN_URL(SEARCH_COORDINATES_IN_URL);
        c.setSEARCH_COORDINATES_IN_URL_2(SEARCH_COORDINATES_IN_URL_2);

        if (jsonList == null) {
            throw new IllegalStateException("json is null");
        }

        for (RowDTO e : jsonList) {
            if (c.coordinates(e.getUrlLocation())) continue;
            PointDTO pointDTO = PointDTO.builder()
                    .id(e.getId().toString())
                    .name(e.getName())
                    .colorsComments(e.getCommentsColor())
                    .colorsName(e.getNameColor())
                    .comments(e.getComments())
                    .locationHref(e.getUrlLocation())
                    .dyskHref(e.getUrlDysk())
                    .x(c.getLat())
                    .y(c.getLon())
                    .build();
            pointDTOS.add(pointDTO);
            log.info(pointDTO);
        }
        return pointDTOS;
    }
}
