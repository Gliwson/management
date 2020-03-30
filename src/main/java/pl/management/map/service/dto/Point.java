package pl.management.map.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    private double x;
    private double y;
    private String id;
    private String name;
    private String comments;
    private String locationHref;
    private String colorsName;
    private String colorsComments;
    private String dyskHref;

}
