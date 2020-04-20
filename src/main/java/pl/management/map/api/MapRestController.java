package pl.management.map.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.management.map.domainmodel.GroupsOfTask;
import pl.management.map.service.DataRepoCSV;
import pl.management.map.service.dto.PointDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MapRestController {

    private final DataRepoCSV dataRepo;

    //Todo replace the point with group
    @GetMapping("/points")
    public List<PointDTO> getTasksFromGroup(@RequestParam(name = "point") GroupsOfTask point) {
        return dataRepo.getPointByDifference(point);

    }

    @GetMapping()
    public String getTest() {
        return "DONE";

    }

}
