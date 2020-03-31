package pl.management.map.web.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.management.map.config.ConfigProperties;
import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.repository.DataRepo;
import pl.management.map.service.csv.ImportWithSheetsGoogle;

import java.io.IOException;


@Log4j2
@RestController()
public class MapRestController {

    private DataRepo dataRepo;
    private ImportWithSheetsGoogle importWithSheetsGoogle;
    private ConfigProperties config;

    public MapRestController(DataRepo dataRepo, ImportWithSheetsGoogle importWithSheetsGoogle, ConfigProperties config) {
        this.dataRepo = dataRepo;
        this.importWithSheetsGoogle = importWithSheetsGoogle;
        this.config = config;
    }


    @GetMapping("/reload")
    public String reloadData() {
        dataRepo.clear();
        try {
            importWithSheetsGoogle.getRow();
        } catch (IOException | BlankSheetException e) {
            e.printStackTrace();
            return "ERROR";
        }
        return "DONE";
    }

    @GetMapping("/testowanie")
    public String reloadData2() {
        return config.getEnviroment2();
    }
}
