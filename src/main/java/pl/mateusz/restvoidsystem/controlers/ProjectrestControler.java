package pl.mateusz.restvoidsystem.controlers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.restvoidsystem.model.dtos.ProjectCountVoiceDto;
import pl.mateusz.restvoidsystem.model.dtos.ProjectDto;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Vote;
import pl.mateusz.restvoidsystem.model.entity.Voter;
import pl.mateusz.restvoidsystem.model.repository.ProjectRepository;
import pl.mateusz.restvoidsystem.model.repository.VoteRepository;
import pl.mateusz.restvoidsystem.model.repository.VoterRepository;

import java.util.*;

@Controller
public class ProjectrestControler {

    ProjectRepository projectRepository;
    VoteRepository voteRepository;
    VoterRepository voterRepository;

    @Autowired
    public ProjectrestControler(ProjectRepository projectRepository, VoteRepository voteRepository, VoterRepository voterRepository) {
        this.projectRepository = projectRepository;
        this.voteRepository = voteRepository;
        this.voterRepository = voterRepository;
    }

    @GetMapping("/api/project")
    public ResponseEntity<List<ProjectDto>> getProject(){
        List<Project> projectList = projectRepository.findAll();

        List<ProjectDto> projectDtoList = new ArrayList<>();

        for (Project project : projectList) {
            projectDtoList.add((new ModelMapper().map(project, ProjectDto.class)));
        }

        Collections.sort(projectDtoList, new Comparator<ProjectDto>() {
            @Override
            public int compare(ProjectDto o1, ProjectDto o2) {
                return o1.getProjectName().compareTo(o2.getProjectName());
            }
        });

        return ResponseEntity.ok(projectDtoList);
    }

//    @RequestMapping(value = "/api/activeproject", method = RequestMethod.POST)
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

    private int voteYes;
    private int voteNo;

    @GetMapping("/api/countvote/{project_Id}")
    public ResponseEntity<ProjectCountVoiceDto> countVote(@PathVariable Long project_Id){

        voteYes=0;
        voteNo=0;
        List<Vote> voteList = new ArrayList<>();
        voteList = voteRepository.findByProject_Id(project_Id);

        for (Vote vote : voteList) {

                if (vote.getVoteValue().equals(1)){
                    voteYes++;
                }else if(vote.getVoteValue().equals(0)){
                    voteNo++;
                }        }


        Optional<Project> project = projectRepository.findById(project_Id);

        Project project1 = project.get();
        ProjectCountVoiceDto projectCountVoiceDto = new ProjectCountVoiceDto();
        project1.setVoiceYes(voteYes);
        project1.setVoiceNo(voteNo);

        return ResponseEntity.ok().body((new ModelMapper().map(project1, ProjectCountVoiceDto.class)));
    }
}
