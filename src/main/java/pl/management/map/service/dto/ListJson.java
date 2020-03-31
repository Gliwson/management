package pl.management.map.service.dto;

import lombok.Data;
import pl.management.map.domain.Row;

import java.util.ArrayList;
import java.util.List;


@Data
public class ListJson {

    private List<Row> user = new ArrayList<>();

}
