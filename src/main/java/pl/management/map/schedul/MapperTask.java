package pl.management.map.schedul;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import pl.management.domainmodel.*;

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

    public List<TaskVersion> pointDtoToTaskVersion(List<PointDTO> pointDTOList) {
        List<TaskVersion> taskVersions = new ArrayList<>();

        for (PointDTO point : pointDTOList) {
            TaskVersion taskVersion = new TaskVersion();
            Set<Task> tasks = new HashSet<>();
            Integer id = Integer.valueOf(point.getId());
            Optional<TaskVersion> optionalTaskVersion;
            Task task = Task.builder()
                    .name(point.getName())
                    .position(Integer.valueOf(point.getId()))
                    .comments(point.getComments())
                    .colorsComments(point.getColorsComments())
                    .colorsName(point.getColorsName())
                    .coordinateX(point.getX())
                    .coordinateY(point.getY())
                    .urlDysk(point.getDyskHref())
                    .urlLocation(point.getLocationHref())
                    .lastModifiedDate(Instant.now())
                    .taskVersion(taskVersion)
                    .build();
            //TODO reduce the number of queries
            if (taskRepository.existsByPosition(id)) {
                Optional<Task> firstByPosition = taskRepository.findFirstByPosition(id);
                try {
                    Task firstTask = firstByPosition.orElseThrow(() -> new NotFoundException("Task not found"));
                    optionalTaskVersion = taskVersionRepository.findById(firstTask.getTaskVersion().getId());
                    TaskVersion taskVersion1 = optionalTaskVersion.orElseThrow(() -> new NotFoundException("TaskVersion id not found"));

                    task.setTaskVersion(taskVersion1);
                    taskVersion1.getTask().add(task);
                    taskVersions.add(taskVersion1);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                tasks.add(task);
                taskVersion.setTask(tasks);
                taskVersions.add(taskVersion);
            }
        }
        return taskVersions;
    }
}
