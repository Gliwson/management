package pl.management.map.service;

import org.springframework.stereotype.Service;
import pl.management.domainmodel.PointDTO;
import pl.management.domainmodel.Task;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointMapper {

    public List<PointDTO> taskToPointDtos(List<Task> taks) {
        List<PointDTO> pointDTOS = new ArrayList<>();
        for (Task task : taks) {
            PointDTO pointDTO = PointDTO.builder()
                    .id(task.getId().toString())
                    .name(task.getName())
                    .comments(task.getComments())
                    .colorsComments(task.getColorsComments())
                    .colorsName(task.getColorsName())
                    .dyskHref(task.getUrlDysk())
                    .locationHref(task.getUrlLocation())
                    .x(task.getCoordinateX())
                    .y(task.getCoordinateY())
                    .build();
            pointDTOS.add(pointDTO);
        }
        return pointDTOS;
    }
}
