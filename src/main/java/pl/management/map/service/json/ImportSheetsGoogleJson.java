package pl.management.map.service.json;


import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.management.config.ConfigProperties;
import pl.management.map.service.dto.ListJsonDTO;
import pl.management.map.service.dto.PointDTO;
import pl.management.map.service.dto.RowDTO;
import pl.management.map.service.mapper.MapperJsonToPointDto;

import java.util.List;

@Log4j2
@Service
public class ImportSheetsGoogleJson {

    private final ConfigProperties configProperties;
    private final MapperJsonToPointDto mapperJsonToPointDto;

    public ImportSheetsGoogleJson(ConfigProperties configProperties, MapperJsonToPointDto mapperJsonToPointDto) {
        this.configProperties = configProperties;
        this.mapperJsonToPointDto = mapperJsonToPointDto;
    }

    public List<RowDTO> getJson() {
        RestTemplate restTemplate = new RestTemplate();
        String stringJson = restTemplate.getForObject(configProperties.getUrlJson(), String.class);
        Gson gson = new Gson();
        ListJsonDTO user = gson.fromJson(stringJson, ListJsonDTO.class);
        return user.getUser();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "10 * 6-20 * * *")
    public List<PointDTO> getJson2() {
        log.info("Execute");
        List<RowDTO> json = getJson();
        return mapperJsonToPointDto.map(json);
    }


}
