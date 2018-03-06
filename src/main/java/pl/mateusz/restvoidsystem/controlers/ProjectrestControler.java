package pl.mateusz.restvoidsystem.controlers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.restvoidsystem.model.dtos.ProjectDto;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.repository.ProjectRepository;

import java.util.*;

@Controller
public class ProjectrestControler {

    ProjectRepository projectRepository;

    @Autowired
    public ProjectrestControler(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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

        project.ifPresent(project1 -> {
            project1.setActive(false);
            project1.setId(projectId);

            projectRepository.save(project1);
        });

        return ResponseEntity.ok().body((new ModelMapper()).map(project2, ProjectDto.class));
    }
}
