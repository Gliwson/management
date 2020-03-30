package pl.management.map.service.dto;

import lombok.Data;
import pl.management.map.domain.TableJson;

import java.util.ArrayList;
import java.util.List;


@Data
public class ListJson {

    private List<TableJson> user = new ArrayList<>();

}
