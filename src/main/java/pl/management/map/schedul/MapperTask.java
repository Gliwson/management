package pl.management.map.schedul;

import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.management.domainmodel.*;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class MapperTask {

    private final TaskVersionRepository taskVersionRepository;

    private final TaskRepository taskRepository;

    public MapperTask(TaskVersionRepository taskVersionRepository, TaskRepository taskRepository) {
        this.taskVersionRepository = taskVersionRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<TaskVersion> pointDtoToTaskVersion(List<PointDTO> pointDTOList) {
        log.info("start session hibernate in pointDtoToTaskVersion");
        List<TaskVersion> taskVersions = new ArrayList<>();
        Set<PointDTO> pointDTOSet = new HashSet<>();

        List<Task> allWithLastModifiedDate = taskRepository.findAllWithLastModifiedDate();

        log.info(allWithLastModifiedDate.size());

        List<PointDTO> addIfThereIsNo = addIfThereIsNo(pointDTOList, allWithLastModifiedDate);
        List<PointDTO> filterGetName = filterGetName(pointDTOList, allWithLastModifiedDate);
        List<PointDTO> filterGetComments = filterGetComments(pointDTOList, allWithLastModifiedDate);
        List<PointDTO> filterGetColorsComments = filterGetColorsComments(pointDTOList, allWithLastModifiedDate);
        List<PointDTO> filterGetColorsName = filterGetColorsName(pointDTOList, allWithLastModifiedDate);

        pointDTOSet.addAll(addIfThereIsNo);
        pointDTOSet.addAll(filterGetName);
        pointDTOSet.addAll(filterGetComments);
        pointDTOSet.addAll(filterGetColorsComments);
        pointDTOSet.addAll(filterGetColorsName);

        log.info(pointDTOSet.size() + " points are updated");

        for (PointDTO point : pointDTOSet) {
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
        log.info("end session hibernate in pointDtoToTaskVersion");
        return taskVersions;
    }

    private List<PointDTO> addIfThereIsNo(List<PointDTO> pointDTOList, List<Task> allWithLastModifiedDate) {
        return pointDTOList.stream().filter(pointDTO -> {
            Integer position = Integer.valueOf(pointDTO.getId());
            long count = allWithLastModifiedDate.stream().filter(task -> task.getPosition() == null || task.getPosition().equals(position)).count();
            return count == 0;
        }).collect(Collectors.toList());

    }

    private List<PointDTO> filterGetName(List<PointDTO> pointDTOList, List<Task> allWithLastModifiedDate) {
        return pointDTOList.stream()
                .filter(pointDTO -> {
                    Integer position = Integer.valueOf(pointDTO.getId());
                    Optional<Task> optionalTask = allWithLastModifiedDate.stream()
                            .filter(task -> task.getPosition().equals(position))
                            .findFirst();
                    return optionalTask
                            .filter(task -> !pointDTO.getName().equals(task.getName()))
                            .isPresent();
                })
                .collect(Collectors.toList());
    }

    private List<PointDTO> filterGetComments(List<PointDTO> pointDTOList, List<Task> allWithLastModifiedDate) {
        return pointDTOList.stream()
                .filter(pointDTO -> {
                    Integer position = Integer.valueOf(pointDTO.getId());
                    Optional<Task> optionalTask = allWithLastModifiedDate.stream()
                            .filter(task -> task.getPosition().equals(position))
                            .findFirst();
                    return optionalTask
                            .filter(task -> !pointDTO.getComments().equals(task.getComments()))
                            .isPresent();
                })
                .collect(Collectors.toList());
    }

    private List<PointDTO> filterGetColorsComments(List<PointDTO> pointDTOList, List<Task> allWithLastModifiedDate) {
        return pointDTOList.stream()
                .filter(pointDTO -> {
                    Integer position = Integer.valueOf(pointDTO.getId());
                    Optional<Task> optionalTask = allWithLastModifiedDate.stream()
                            .filter(task -> task.getPosition().equals(position))
                            .findFirst();
                    return optionalTask
                            .filter(task -> !pointDTO.getColorsComments().equals(task.getColorsComments()))
                            .isPresent();
                })
                .collect(Collectors.toList());
    }

    private List<PointDTO> filterGetColorsName(List<PointDTO> pointDTOList, List<Task> allWithLastModifiedDate) {
        return pointDTOList.stream()
                .filter(pointDTO -> {
                    Integer position = Integer.valueOf(pointDTO.getId());
                    Optional<Task> optionalTask = allWithLastModifiedDate.stream()
                            .filter(task -> task.getPosition().equals(position))
                            .findFirst();
                    return optionalTask
                            .filter(task -> !pointDTO.getColorsName().equals(task.getColorsName()))
                            .isPresent();
                })
                .collect(Collectors.toList());
    }
}
