package pl.management.map.schedul;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.management.domainmodel.PointDTO;
import pl.management.domainmodel.TaskVersion;
import pl.management.domainmodel.TaskVersionRepository;
import pl.management.map.schedul.factory.ImportFactoryService;
import pl.management.map.schedul.model.Format;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Log4j2
public class SavePointDto {

    private final ImportFactoryService importFactoryService;
    private final TaskVersionRepository taskVersionRepository;
    private final MapperTask mapperTask;

    public SavePointDto(ImportFactoryService importFactoryService, TaskVersionRepository taskVersionRepository, MapperTask mapperTask) {
        this.importFactoryService = importFactoryService;
        this.taskVersionRepository = taskVersionRepository;
        this.mapperTask = mapperTask;
    }

    public void saveTODatabase() {
        List<PointDTO> strategy = importFactoryService.findStrategy(Format.JSON);
        List<TaskVersion> taskVersions = mapperTask.pointDtoToTaskVersion(strategy);
        taskVersionSaveToDatabase(taskVersions);
    }

    @Transactional
    public void taskVersionSaveToDatabase(List<TaskVersion> taskVersions) {
        taskVersionRepository.saveAll(taskVersions);
    }
}
