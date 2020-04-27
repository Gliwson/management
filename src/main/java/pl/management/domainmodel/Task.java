package pl.management.domainmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "TASK_VERSION_ID")
    private TaskVersion taskVersion;

    private Integer position;
    private String name;
    private String status;
    private String date;

    @Column(length = 1000)
    private String comments;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private Instant lastModifiedDate;
    private GroupsOfTask groupsOfPoints;

    private String UrlDysk;
    private String UrlLocation;

    private String colorsName;
    private String colorsComments;

    private double coordinateX;
    private double coordinateY;

    public Task() {
    }
}
