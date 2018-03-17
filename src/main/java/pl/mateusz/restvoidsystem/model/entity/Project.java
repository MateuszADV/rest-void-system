package pl.mateusz.restvoidsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @OneToMany(mappedBy = "project")
    private Set<Vote> voteSet;
}
