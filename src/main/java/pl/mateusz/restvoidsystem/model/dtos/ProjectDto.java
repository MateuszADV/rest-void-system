package pl.mateusz.restvoidsystem.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {

    private Long id;
    private String projectName;
    private String projectDescryption;
    private Boolean active;
}
