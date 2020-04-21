package pl.management.map.schedul.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ListJsonDTO {

    private List<RowDTO> user = new ArrayList<>();

}
