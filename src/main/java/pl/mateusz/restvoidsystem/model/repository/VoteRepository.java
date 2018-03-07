package pl.mateusz.restvoidsystem.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.restvoidsystem.model.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
