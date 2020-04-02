package pl.management.map.service.json;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.management.map.config.ConfigProperties;
import pl.management.map.service.csv.DataRepoCSV;
import pl.management.map.service.dto.ListJsonDTO;
import pl.management.map.service.dto.PointDTO;
import pl.management.map.service.dto.RowDTO;
import pl.management.map.service.mapper.MapperJsonToPointDto;

import java.util.List;

@Service
public class ImportSheetsGoogleJson {

    private ConfigProperties configProperties;
    private DataRepoCSV dataRepo;
    private MapperJsonToPointDto mapperJsonToPointDto;

    @Value("${urlJson}")
    private static String URL_JSON;

    public ImportSheetsGoogleJson(ConfigProperties configProperties, DataRepoCSV dataRepo, MapperJsonToPointDto mapperJsonToPointDto) {
        this.configProperties = configProperties;
        this.dataRepo = dataRepo;
        this.mapperJsonToPointDto = mapperJsonToPointDto;
    }

    public List<RowDTO> getJson() {
        RestTemplate restTemplate = new RestTemplate();
        String stringJson = restTemplate.getForObject(configProperties.getUrlJson(), String.class);
        Gson gson = new Gson();
        ListJsonDTO user = gson.fromJson(stringJson, ListJsonDTO.class);
        return user.getUser();
    }

    public List<PointDTO> getJson2() {
        List<RowDTO> json = getJson();
        return mapperJsonToPointDto.map(json);
    }


}
