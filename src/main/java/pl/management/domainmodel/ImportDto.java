package pl.management.domainmodel;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ImportDto {
    private String urlDysk;
    private String urlLocation;
    private String colorsComments;
    private String comments;
    private String CoordinateX;
    private String CoordinateY;
    private Instant lastModifiedDate;
    private String name;
    private String position;
    private String taskVersionId;

    public ImportDto(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
