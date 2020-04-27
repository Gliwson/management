package pl.management.domainmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskVersionRepository extends JpaRepository<TaskVersion, Integer> {

}
