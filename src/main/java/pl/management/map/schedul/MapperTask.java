package pl.management.map.schedul;

import org.springframework.stereotype.Service;
import pl.management.domainmodel.Task;
import pl.management.domainmodel.TaskRepository;
import pl.management.domainmodel.TaskVersion;
import pl.management.domainmodel.TaskVersionRepository;
import pl.management.map.schedul.dto.PointDTO;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
public class MapperTask {

    private final TaskVersionRepository taskVersionRepository;

    private final TaskRepository taskRepository;

    public MapperTask(TaskVersionRepository taskVersionRepository, TaskRepository taskRepository) {
        this.taskVersionRepository = taskVersionRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<TaskVersion> pointDtoToTaskVersion(List<PointDTO> pointDTOList) {
        List<TaskVersion> taskVersions = new ArrayList<>();

        for (PointDTO point : pointDTOList) {
            TaskVersion taskVersion = new TaskVersion();
            Set<Task> tasks = new HashSet<>();
            Integer id = null;
            Optional<TaskVersion> byId;
            Task task = Task.builder()
                    .name(point.getName())
                    .position(Integer.valueOf(point.getId()))
                    .comments(point.getComments())
                    .colorsComments(point.getColorsComments())
                    .colorsName(point.getColorsName())
                    .coordinateX(point.getX())
                    .coordinateY(point.getY())
                    .UrlDysk(point.getDyskHref())
                    .UrlLocation(point.getLocationHref())
                    .lastModifiedDate(Instant.now())
                    .taskVersion(taskVersion)
                    .build();

            if (taskRepository.existsByPosition(Integer.valueOf(point.getId()))) {
                id = Integer.valueOf(point.getId());
                Optional<Task> firstByPosition = taskRepository.findFirstByPosition(id);
                Task task1 = firstByPosition.get();
                byId = taskVersionRepository.findById(task1.getTaskVersion().getId());
                TaskVersion taskVersion1 = byId.get();
                task.setTaskVersion(taskVersion1);
                taskVersion1.getTask().add(task);
                taskVersions.add(taskVersion1);
            } else {
                tasks.add(task);
                taskVersion.setTask(tasks);
                taskVersions.add(taskVersion);
            }
        }
        return taskVersions;
    }
}
