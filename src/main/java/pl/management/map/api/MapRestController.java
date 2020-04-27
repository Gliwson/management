package pl.management.map.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.management.domainmodel.GroupsOfTask;
import pl.management.domainmodel.ImportDto;
import pl.management.domainmodel.TaskRepository;
import pl.management.map.schedul.SavePointDto;
import pl.management.map.schedul.dto.PointDTO;
import pl.management.map.service.DataRepoCSV;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class MapRestController {

    private final DataRepoCSV dataRepo;
    private final SavePointDto savePointDto;
    private final TaskRepository taskRepository;

    //Todo replace the point with group
    @GetMapping("/points")
    public List<PointDTO> getTasksFromGroup(@RequestParam(name = "point") GroupsOfTask point) {
        return dataRepo.getPointByDifference(point);
    }

    @GetMapping
    public List<ImportDto> getTasksFromGroup2() {
        List<ImportDto> znajdzto = taskRepository.znajdzto2();
        return znajdzto;
    }

}
