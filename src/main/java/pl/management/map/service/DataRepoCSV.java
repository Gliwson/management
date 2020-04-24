package pl.management.map.service;

import org.springframework.stereotype.Repository;
import pl.management.domainmodel.GroupsOfTask;
import pl.management.map.schedul.dto.PointDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataRepoCSV {

    private List<PointDTO> pointList;
    private List<PointDTO> invoiced;
    private List<PointDTO> done;
    private List<PointDTO> comments;
    private List<PointDTO> off;
    private List<PointDTO> other;

    public DataRepoCSV() {
        this.pointList = new ArrayList<>();
    }

    public List<PointDTO> getPointList() {
        return pointList;
    }

    public void addPoint(PointDTO newPoint) {
        this.pointList.add(newPoint);
        invoiced = getPointList().stream().filter(point -> point.getColorsName()
                .equals("#000000")).collect(Collectors.toList());
        done = getPointList()
                .stream().filter(point -> point.getColorsName()
                        .equals("#00ff00") && point.getColorsComments().equals("#00ff00")).collect(Collectors.toList());

        comments = getPointList().stream()
                .filter(point -> point.getColorsComments().equals("#ff0000")).collect(Collectors.toList());

        off = getPointList().stream()
                .filter(point -> point.getColorsComments().equals("#ff00ff")).collect(Collectors.toList());

        other = getPointList().stream()
                .filter(point -> !point.getColorsComments().equals("#ff00ff") && !point.getColorsComments().equals("#ff0000")
                        && !point.getColorsName().equals("#00ff00") && !point.getColorsComments().equals("#00ff00") &&
                        !point.getColorsName().equals("#000000"))
                .collect(Collectors.toList());
    }

    public List<PointDTO> getInvoiced() {
        return invoiced;
    }

    public List<PointDTO> getDone() {
        return done;
    }

    public List<PointDTO> getComments() {
        return comments;
    }

    public List<PointDTO> getOff() {
        return off;
    }

    public List<PointDTO> getOther() {
        return other;
    }

    public void clear() {
        pointList = new ArrayList<>();
        invoiced = new ArrayList<>();
        done = new ArrayList<>();
        comments = new ArrayList<>();
        off = new ArrayList<>();
        other = new ArrayList<>();
    }

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
}
