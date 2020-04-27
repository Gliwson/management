package pl.management.map.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PointMapDto {
    private Instant lastModifiedDate;
    private Integer id;
    private String name;
    private String comments;

    private String colorsName;
    private String colorsComments;

    private String urlLocation;
    private String urlDysk;

    private Double coordinateX;
    private Double coordinateY;

    public PointMapDto(Instant lastModifiedDate, Integer id, String name, String comments, String colorsName, String colorsComments, String urlLocation, String urlDysk, Double coordinateX, Double coordinateY) {
        this.lastModifiedDate = lastModifiedDate;
        this.id = id;
        this.name = name;
        this.comments = comments;
        this.colorsName = colorsName;
        this.colorsComments = colorsComments;
        this.urlLocation = urlLocation;
        this.urlDysk = urlDysk;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}
