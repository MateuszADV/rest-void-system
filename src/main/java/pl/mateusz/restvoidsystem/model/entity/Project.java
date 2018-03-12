package pl.mateusz.restvoidsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    public Project(String projectName, String projectDescryption, Boolean active) {
        this.projectName = projectName;
        this.projectDescryption = projectDescryption;
        this.active = active;
    }

    public Project(String projectName, String projectDescryption, Boolean active, Date closeProject) {
        this.projectName = projectName;
        this.projectDescryption = projectDescryption;
        this.active = active;
        this.closeProject = closeProject;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String projectName;
    private String projectDescryption;
    private Boolean active;
    private Date closeProject;
}
