package pl.management.map.repository;

import org.springframework.stereotype.Repository;
import pl.management.map.service.dto.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataRepo {

    private List<Point> pointList;
    private List<Point> invoiced;
    private List<Point> done;
    private List<Point> comments;
    private List<Point> off;
    private List<Point> other;

    public DataRepo() {
        this.pointList = new ArrayList<>();
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void addPoint(Point newPoint) {
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

    public List<Point> getInvoiced() {
        return invoiced;
    }

    public List<Point> getDone() {
        return done;
    }

    public List<Point> getComments() {
        return comments;
    }

    public List<Point> getOff() {
        return off;
    }

    public List<Point> getOther() {
        return other;
    }

    public void clear() {
        pointList.clear();
        invoiced.clear();
        done.clear();
        comments.clear();
        off.clear();
        other.clear();
    }
}
