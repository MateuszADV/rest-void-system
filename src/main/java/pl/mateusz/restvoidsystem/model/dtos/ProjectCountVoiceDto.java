package pl.mateusz.restvoidsystem.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectCountVoiceDto {

    private String projectName;
    private String projectDescryption;
    private Boolean active;
    private Date closeProject;

    private int voiceYes;
    private int voiceNo;
}
