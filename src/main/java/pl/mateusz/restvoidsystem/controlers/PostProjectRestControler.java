package pl.mateusz.restvoidsystem.controlers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.restvoidsystem.model.dtos.ProjectDto;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Vote;
import pl.mateusz.restvoidsystem.model.entity.Voter;
import pl.mateusz.restvoidsystem.model.repository.ProjectRepository;
import pl.mateusz.restvoidsystem.model.repository.VoteRepository;
import pl.mateusz.restvoidsystem.model.repository.VoterRepository;

import java.util.Date;
import java.util.Optional;

@RestController
public class PostProjectRestControler {

    private ProjectRepository projectRepository;
    private VoteRepository voteRepository;
    private VoterRepository voterRepository;

    @Autowired
    public PostProjectRestControler(ProjectRepository projectRepository, VoteRepository voteRepository, VoterRepository voterRepository){
        this.projectRepository = projectRepository;
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
    }


    //Zmiana aktywnego projektu na nie aktywny
    @PostMapping("/api/activeproject/{projectId}")
    public ResponseEntity<ProjectDto> projectPatch(@PathVariable Long projectId){

        Optional<Project> project = projectRepository.findById(projectId);

        Project  project2 = project.get();

        ProjectDto projectDto = new ProjectDto();

        if(project2.getActive()) {
            project.ifPresent(project1 -> {
                project1.setActive(false);
                project1.setId(projectId);
                project1.setCloseProject(new Date());

                projectRepository.save(project1);
            });
        }

        return ResponseEntity.ok().body((new ModelMapper()).map(project2, ProjectDto.class));
    }

    //głosowanie na projekt
    @PostMapping("/api/voteproject/{projectId}/{voterId}/{vote1}")
    public ResponseEntity<Vote> voteProject(@PathVariable Long projectId,
                                            @PathVariable Long voterId,
                                            @PathVariable Integer vote1){
        Vote vote = new Vote();
        Optional<Project> project = projectRepository.findById(projectId);
        Project project1 = project.get();

        Optional<Voter> voter = voterRepository.findById(voterId);
        Voter voter1 = voter.get();

        if(project1.getActive()) {
            vote.setProject(project1);
            vote.setVoter(voter1);
            vote.setVoteValue(vote1);

            voteRepository.save(vote);
        }else {
            project1.setProjectDescryption("Projek na który głosujesz jest zakończony");
            vote.setProject(project1);
        }
        return ResponseEntity.ok().body((new ModelMapper().map(vote, Vote.class)));
    }
}
