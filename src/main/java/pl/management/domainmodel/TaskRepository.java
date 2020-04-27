package pl.management.domainmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean existsByPosition(Integer id);

    Optional<Task> findFirstByPosition(Integer id);

    @Query(value = "select new pl.management.domainmodel.ImportDto(max(ta.lastModifiedDate)) " +
            "from Task as ta group by ta.taskVersion")
    List<ImportDto> znajdzto2();
}
