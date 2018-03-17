package pl.mateusz.restvoidsystem.controlers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.restvoidsystem.model.dtos.ProjectCountVoiceDto;
import pl.mateusz.restvoidsystem.model.dtos.ProjectDto;
import pl.mateusz.restvoidsystem.model.dtos.VoteDto;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Vote;
import pl.mateusz.restvoidsystem.model.entity.Voter;
import pl.mateusz.restvoidsystem.model.repository.ProjectRepository;
import pl.mateusz.restvoidsystem.model.repository.VoteRepository;
import pl.mateusz.restvoidsystem.model.repository.VoterRepository;

import java.util.*;

@RestController
public class ProjectRestControler {

    ProjectRepository projectRepository;
    VoteRepository voteRepository;
    VoterRepository voterRepository;

    @Autowired
    public ProjectRestControler(ProjectRepository projectRepository, VoteRepository voteRepository, VoterRepository voterRepository) {
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



    //Zlicznie złosów na dany projekt
    @GetMapping("/api/project/{project_Id}")
    public ResponseEntity<ProjectCountVoiceDto> countVote(@PathVariable Long project_Id){
        int voteYes;
        int voteNo;

        voteYes=0;
        voteNo=0;
        List<Vote> voteList = new ArrayList<>();
        voteList = voteRepository.findByProject_Id(project_Id);

        for (Vote vote : voteList) {

                if (vote.getVoteValue().equals(1)){
                    voteYes++;
                }else if(vote.getVoteValue().equals(0)){
                    voteNo++;
                }
        }

        Optional<Project> project = projectRepository.findById(project_Id);

        Project project1 = project.get();
        ProjectCountVoiceDto projectCountVoiceDto = new ProjectCountVoiceDto();

        projectCountVoiceDto = (new ModelMapper().map(project1, ProjectCountVoiceDto.class));

        projectCountVoiceDto.setVoiceYes(voteYes);
        projectCountVoiceDto.setVoiceNo(voteNo);

        return ResponseEntity.ok().body(projectCountVoiceDto);
    }

    //Zmiana aktywnego projektu na nie aktywny
    @PostMapping("/api/project/active/{projectId}")
    public ResponseEntity<ProjectDto> projectPatch(@PathVariable Long projectId){

        Optional<Project> project = projectRepository.findById(projectId);

        Project  project2 = project.get();

        //ProjectDto projectDto = new ProjectDto();

        if(project2.getActive()) {
            project.ifPresent(project1 -> {
                project1.setActive(false);
                //project1.setId(projectId);   jest nie potrzebne
                project1.setCloseProject(new Date());

                projectRepository.save(project1);
            });
        }

        return ResponseEntity.ok().body((new ModelMapper()).map(project2, ProjectDto.class));
    }

    //głosowanie na projekt
    @PostMapping("/api/project/vote/{projectId}/{voterId}/{vote1}")
    public ResponseEntity<VoteDto> voteProject(@PathVariable Long projectId,
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
            //nie robiućw ten sposób lepiej dać wyjątek
            project1.setProjectDescryption("Projek na który głosujesz jest zakończony");
            vote.setProject(project1);
        }
        return ResponseEntity.ok().body((new ModelMapper().map(vote, VoteDto.class)));
    }
}
