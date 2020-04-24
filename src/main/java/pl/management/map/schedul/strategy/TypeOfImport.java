package pl.management.map.schedul.strategy;

import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.schedul.dto.PointDTO;
import pl.management.map.schedul.model.Format;

import java.io.IOException;
import java.util.List;

public interface TypeOfImport {

    List<PointDTO> map(String url) throws BlankSheetException, IOException;

    Format getStrategyName();
}
