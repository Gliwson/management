package pl.management.domainmodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TASK_VERSION")
@Getter
@Setter
@Data
public class TaskVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @OneToMany(mappedBy = "taskVersion", cascade = {CascadeType.PERSIST})
    private Set<Task> task = new HashSet<>();

    public TaskVersion() {
    }
}
