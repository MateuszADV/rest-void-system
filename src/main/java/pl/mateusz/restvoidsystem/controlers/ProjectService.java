package pl.mateusz.restvoidsystem.controlers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mateusz.restvoidsystem.model.dtos.ProjectCountVoiceDto;
import pl.mateusz.restvoidsystem.model.dtos.ProjectWithStatusDto;
import pl.mateusz.restvoidsystem.model.dtos.VoteDto;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Vote;
import pl.mateusz.restvoidsystem.model.entity.Voter;
import pl.mateusz.restvoidsystem.model.repository.ProjectRepository;
import pl.mateusz.restvoidsystem.model.repository.VoteRepository;
import pl.mateusz.restvoidsystem.model.repository.VoterRepository;

import javax.swing.text.StyledEditorKit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    ProjectRepository projectRepository;
    VoterRepository voterRepository;
    VoteRepository voteRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, VoterRepository voterRepository, VoteRepository voteRepository) {
        this.projectRepository = projectRepository;
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
    }

    public VoteDto makeVote(Long projectId, Long voterId, Integer voteValue) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Project doesn't exist");
        }

        Optional<Voter> voterOptional = voterRepository.findById(voterId);
        if (!voterOptional.isPresent()) {
            throw new RuntimeException("Voter doesn't exist");
        }

        Project project = projectOptional.get();
        if (project.getActive() == false) {
            throw new RuntimeException("Project not active");
        }

        boolean voteExist = voteRepository.existsByProjectIdAndVoterId(projectId, voterId);
        if(voteExist){
            throw new RuntimeException("Vote already exists");
        }

        Vote vote = new Vote(voterOptional.get(),project,voteValue);

        Vote voteSave = voteRepository.save(vote);


        return new ModelMapper().map(vote, VoteDto.class);
    }

    public ProjectWithStatusDto changeStatus(Long projectId, Boolean active){
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(!projectOptional.isPresent()){
            throw new RuntimeException("Project doesn't exist");
        }

        Project project = projectOptional.get();
        if(project.getActive().equals(active)){
            return new ModelMapper().map(project, ProjectWithStatusDto.class);
        }

        project.setActive(active);
        project.setCloseProject(new Date());
        Project saveProject = projectRepository.save(project);
        return new ModelMapper().map(project, ProjectWithStatusDto.class);
    }

    public Optional<ProjectCountVoiceDto> getProjectCountVotes(Long projectId){
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(!projectOptional.isPresent()){
            return Optional.empty();
        }

        int voteYes =0;
        int voteNo = 0;

        List<Vote> votes = voteRepository.findAllByProject_Id(projectId);

        for (Vote vote : votes) {
            Integer voteVaue = vote.getVoteValue();
            if(voteVaue > 0){
                voteYes++;
            }else {
                voteNo++;
            }
        }


        ProjectCountVoiceDto projectCountVoiceDto =
                new ModelMapper().map(projectOptional.get(), ProjectCountVoiceDto.class);

        projectCountVoiceDto.setVoiceYes(voteYes);
        projectCountVoiceDto.setVoiceNo(voteNo);

        //Optional.ofNullable(null);

        return Optional.of(projectCountVoiceDto);
    }

}
