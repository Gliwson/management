package pl.management.map.domain;

import lombok.Data;

@Data
public class Row {
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
