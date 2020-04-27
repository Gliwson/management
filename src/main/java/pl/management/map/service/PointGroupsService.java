package pl.management.map.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.management.domainmodel.GroupsOfTask;
import pl.management.domainmodel.PointDTO;
import pl.management.domainmodel.TaskRepository;
import pl.management.map.service.dto.PointMapDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointGroupsService {

    private final TaskRepository taskRepository;
    private final PointMapper pointMapper;

    public List<PointDTO> getPointByDifference(GroupsOfTask point) {
        switch (point) {
            case INVOICED:
                return getInvoiced();
            case OFF:
                return getOff();
            case DONE:
                return getDone();
            case OTHER:
                return getOther();
            case COMMENTS:
                return getComments();
            case ALL:
                return getPointList();
        }
        return new ArrayList<>();
    }

    public List<PointDTO> getPointList() {
        List<PointMapDto> allWithLastModifiedDate = taskRepository.findAllWithLastModifiedDate();
        return pointMapper.pointMapDtosTOPointDto(allWithLastModifiedDate);
    }

    public List<PointDTO> getInvoiced() {
        return getPointList().stream().filter(point -> point.getColorsName()
                .equals("#000000")).collect(Collectors.toList());
    }

    public List<PointDTO> getDone() {
        return getPointList()
                .stream().filter(point -> point.getColorsName()
                        .equals("#00ff00") && point.getColorsComments()
                        .equals("#00ff00")).collect(Collectors.toList());
    }

    public List<PointDTO> getComments() {
        return getPointList().stream()
                .filter(point -> point.getColorsComments().equals("#ff0000")).collect(Collectors.toList());
    }

    public List<PointDTO> getOff() {
        return getPointList().stream()
                .filter(point -> point.getColorsComments().equals("#ff00ff")).collect(Collectors.toList());
    }

    public List<PointDTO> getOther() {
        return getPointList().stream()
                .filter(point -> !point.getColorsComments().equals("#ff00ff") && !point.getColorsComments().equals("#ff0000")
                        && !point.getColorsName().equals("#00ff00") && !point.getColorsComments().equals("#00ff00") &&
                        !point.getColorsName().equals("#000000"))
                .collect(Collectors.toList());
    }


}
