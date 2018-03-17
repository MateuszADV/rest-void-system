package pl.mateusz.restvoidsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"voter_id", "project_id"})})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Integer voteValue;

    private Date added = new Date();

    public Vote(Voter voter, Project project, Integer voteValue) {
        this.voter = voter;
        this.project = project;
        this.voteValue = voteValue;
    }
}
