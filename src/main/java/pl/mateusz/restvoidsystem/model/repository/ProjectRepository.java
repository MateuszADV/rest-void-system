package pl.mateusz.restvoidsystem.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.restvoidsystem.model.entity.Project;

import java.util.Optional;

public interface ProjectRepository  extends JpaRepository<Project, Long>{

    Optional<Project> findById(Long id);
}
