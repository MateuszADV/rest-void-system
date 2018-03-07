package pl.mateusz.restvoidsystem.model.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Voter;

@Getter
@Setter
public class VoteDto {

    private Long id;
    private Voter voter;
    private Project project;
    private Integer voteValue;

}
