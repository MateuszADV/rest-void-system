package pl.mateusz.restvoidsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String projectName;
    private String projectDescryption;
    private Boolean active;


}
