package pl.management.map.service;

import org.springframework.stereotype.Service;
import pl.management.domainmodel.PointDTO;
import pl.management.map.service.dto.PointMapDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointMapper {

    public List<PointDTO> pointMapDtosTOPointDto(List<PointMapDto> allWithLastModifiedDate) {
        List<PointDTO> pointDTOS = new ArrayList<>();
        for (PointMapDto pointMapDto : allWithLastModifiedDate) {
            PointDTO pointDTO = PointDTO.builder()
                    .id(pointMapDto.getId().toString())
                    .name(pointMapDto.getName())
                    .comments(pointMapDto.getComments())
                    .colorsComments(pointMapDto.getColorsComments())
                    .colorsName(pointMapDto.getColorsName())
                    .dyskHref(pointMapDto.getUrlDysk())
                    .locationHref(pointMapDto.getUrlLocation())
                    .x(pointMapDto.getCoordinateX())
                    .y(pointMapDto.getCoordinateY())
                    .build();
            pointDTOS.add(pointDTO);
        }
        return pointDTOS;
    }
}
