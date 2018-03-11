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
public class GetProjectRestControler {

    ProjectRepository projectRepository;
    VoteRepository voteRepository;
    VoterRepository voterRepository;

    @Autowired
    public GetProjectRestControler(ProjectRepository projectRepository, VoteRepository voteRepository, VoterRepository voterRepository) {
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


    private int voteYes;
    private int voteNo;


    //Zlicznie złosów na dany projekt
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
