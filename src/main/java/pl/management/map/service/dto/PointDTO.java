package pl.management.map.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDTO {

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
