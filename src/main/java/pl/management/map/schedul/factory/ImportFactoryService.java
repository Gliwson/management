package pl.management.map.schedul.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.schedul.dto.PointDTO;
import pl.management.map.schedul.model.Format;
import pl.management.map.schedul.strategy.TypeOfImport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ImportFactoryService {

    private Map<Format, TypeOfImport> strategies;

    @Value("${urlJson}")
    private String URL_JSON;

    @Value("${urlCSV}")
    private String URL_CSV;

    public ImportFactoryService(Set<TypeOfImport> strategySet) {
        createStrategy(strategySet);
    }

    public List<PointDTO> findStrategy(Format strategyName) {
        String url;
        switch (strategyName) {
            case CSV:
                url = URL_CSV;
                break;
            case JSON:
                url = URL_JSON;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + strategyName);
        }

        try {
            return strategies.get(strategyName).map(url);
        } catch (BlankSheetException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createStrategy(Set<TypeOfImport> strategySet) {
        strategies = new HashMap<Format, TypeOfImport>();
        strategySet.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }
}
