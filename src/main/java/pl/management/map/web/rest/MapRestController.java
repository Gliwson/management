package pl.management.map.web.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.management.map.domain.PointDifference;
import pl.management.map.repository.DataRepoCSV;
import pl.management.map.service.dto.PointDTO;
import pl.management.map.service.json.ImportSheetsGoogleJson;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/api")
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

    @GetMapping("/points")
    public List<PointDTO> getPoint(@RequestParam(name = "point") PointDifference point) {
        return dataRepo.getPointByDifference(point);
    }

}
