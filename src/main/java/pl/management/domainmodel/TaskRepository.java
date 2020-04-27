package pl.management.domainmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.management.map.service.dto.PointMapDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean existsByPosition(Integer id);

    Optional<Task> findFirstByPosition(Integer id);

    @Query(value = "select new pl.management.map.service.dto.PointMapDto(max(ta.lastModifiedDate), " +
            "ta.position, ta.name, ta.comments, ta.colorsName, ta.colorsComments, ta.urlLocation," +
            "ta.urlDysk, ta.coordinateX, ta.coordinateY ) from Task as ta group by ta.taskVersion")
    List<PointMapDto> findAllWithLastModifiedDate();
}
