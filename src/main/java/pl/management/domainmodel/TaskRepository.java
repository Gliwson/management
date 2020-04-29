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

    @Query("SELECT tk \n" +
            "FROM Task tk \n" +
            "WHERE tk.lastModifiedDate in (SELECT MAX(t.lastModifiedDate) FROM Task t GROUP BY t.position)")
    List<Task> findAllWithLastModifiedDate();
}
