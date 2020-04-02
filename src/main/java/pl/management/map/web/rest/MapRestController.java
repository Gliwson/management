package pl.management.map.web.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.management.map.service.csv.DataRepoCSV;
import pl.management.map.service.dto.PointDTO;
import pl.management.map.service.json.ImportSheetsGoogleJson;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class MapRestController {

    private DataRepoCSV dataRepo;
    private ImportSheetsGoogleJson importSheetsGoogleJson;

    public MapRestController(DataRepoCSV dataRepo, ImportSheetsGoogleJson importSheetsGoogleJson) {
        this.dataRepo = dataRepo;
        this.importSheetsGoogleJson = importSheetsGoogleJson;
    }


    @GetMapping("/reload")
    public String reloadData() {
        dataRepo.clear();
        importSheetsGoogleJson.getJson2();
        return "DONE";
    }


    @GetMapping("/test")
    public List<PointDTO> reloadData2() {
        return dataRepo.getPointList().stream().limit(20).collect(Collectors.toList());
    }
}
