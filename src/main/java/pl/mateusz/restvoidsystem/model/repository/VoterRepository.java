package pl.mateusz.restvoidsystem.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.restvoidsystem.model.entity.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {

}
