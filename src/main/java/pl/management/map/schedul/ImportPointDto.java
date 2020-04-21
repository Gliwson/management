package pl.management.map.schedul;

import pl.management.map.exceptions.BlankSheetException;
import pl.management.map.schedul.format.TypeOfImport;
import pl.management.map.service.dto.PointDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportPointDto {

    private TypeOfImport typeOfImport;

    public List<PointDTO> anImport() {
        try {
            return typeOfImport.map();
        } catch (BlankSheetException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void setTypeOfImport(TypeOfImport typeOfImport) {
        this.typeOfImport = typeOfImport;
    }

}
