package pl.management.map.schedul.format;

import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.service.dto.PointDTO;

import java.io.IOException;
import java.util.List;

public interface TypeOfImport {
    List<PointDTO> map() throws BlankSheetException, IOException;
}
