package pl.management.map.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.management.map.domain.PointDifference;
import pl.management.map.repository.DataRepoCSV;
import pl.management.map.service.dto.PointDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MapRestController {

    private final DataRepoCSV dataRepo;

    //Todo replace the point with group
    @GetMapping("/points")
    public List<PointDTO> getPoint(@RequestParam(name = "point") PointDifference point) {
        return dataRepo.getPointByDifference(point);
    }

}
