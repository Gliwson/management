package pl.management.map.domainmodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    private LocalDateTime recordingTime;
    private double coordinateX;
    private double coordinateY;
    private GroupsOfTask groupsOfPoints;
    private Integer position;
    private String name;
    private String status;
    private String date;
    private String comments;
    private String UrlDysk;
    private String UrlLocation;
    private String colorsName;
    private String colorsComments;


}
