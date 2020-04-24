package pl.management.map.schedul;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.management.map.schedul.dto.PointDTO;
import pl.management.map.schedul.factory.ImportFactoryService;
import pl.management.map.schedul.model.Format;

import java.util.List;

@Component
@Log4j2
public class SavePointDto {

    private final ImportFactoryService importFactoryService;

    public SavePointDto(ImportFactoryService importFactoryService) {
        this.importFactoryService = importFactoryService;
    }

    public List<PointDTO> start() {
        List<PointDTO> strategy = importFactoryService.findStrategy(Format.JSON);
        strategy.forEach(PointDTO::getId);
        return strategy;
    }
}
