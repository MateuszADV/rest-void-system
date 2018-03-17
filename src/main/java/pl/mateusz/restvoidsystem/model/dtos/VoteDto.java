package pl.mateusz.restvoidsystem.model.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Voter;

import java.util.Date;

@Getter
@Setter
@ToString
public class VoteDto {

    private Long id;
    private Voter voter;
    private Project project;
    private Integer voteValue;
    private Date added;

}
