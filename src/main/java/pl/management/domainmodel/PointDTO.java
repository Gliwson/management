package pl.management.domainmodel;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PointDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    private String id;
    private String name;
    private String comments;
    private String locationHref;
    private String colorsName;
    private String colorsComments;
    private String dyskHref;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointDTO pointDTO = (PointDTO) o;

        if (Double.compare(pointDTO.x, x) != 0) return false;
        if (Double.compare(pointDTO.y, y) != 0) return false;
        if (id != null ? !id.equals(pointDTO.id) : pointDTO.id != null) return false;
        if (name != null ? !name.equals(pointDTO.name) : pointDTO.name != null) return false;
        if (comments != null ? !comments.equals(pointDTO.comments) : pointDTO.comments != null) return false;
        if (locationHref != null ? !locationHref.equals(pointDTO.locationHref) : pointDTO.locationHref != null)
            return false;
        if (colorsName != null ? !colorsName.equals(pointDTO.colorsName) : pointDTO.colorsName != null) return false;
        if (colorsComments != null ? !colorsComments.equals(pointDTO.colorsComments) : pointDTO.colorsComments != null)
            return false;
        return dyskHref != null ? dyskHref.equals(pointDTO.dyskHref) : pointDTO.dyskHref == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (locationHref != null ? locationHref.hashCode() : 0);
        result = 31 * result + (colorsName != null ? colorsName.hashCode() : 0);
        result = 31 * result + (colorsComments != null ? colorsComments.hashCode() : 0);
        result = 31 * result + (dyskHref != null ? dyskHref.hashCode() : 0);
        return result;
    }
}
