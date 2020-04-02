package pl.management.map.service.dto;

import lombok.Data;

@Data
public class RowDTO {
    private Integer id;
    private Integer position;
    private String name;
    private String status;
    private String date;
    private String comments;
    private String UrlDysk;
    private String UrlLocation;
    private String nameColor;
    private String CommentsColor;
}
