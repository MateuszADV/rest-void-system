package pl.mateusz.restvoidsystem.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mateusz.restvoidsystem.model.dtos.ProjectCountVoiceDto;
import pl.mateusz.restvoidsystem.model.entity.Project;

import java.util.Optional;

public interface ProjectRepository  extends JpaRepository<Project, Long>{

    Optional<Project> findById(Long id);
/*
    @Query("SELECT new pl.mateusz.restvoidsystem.model.dtos.ProjectCountVoiceDto( p.projectName, p.projectDescryption, p.active, sum(case when v.voteValue > 0 THEN 1 else 0 end),sum(case when v.voteValue  < 1 THEN 1 else 0 end)),FROM Project p LEFT JOIN Vote v ON p.id = v.project WHERE p.id = :projectId")
    Optional<ProjectCountVoiceDto> findByIdWithVote(@Param("projectId") Long projectId);*/
}
