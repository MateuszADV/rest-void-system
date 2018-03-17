package pl.mateusz.restvoidsystem.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.restvoidsystem.model.entity.Vote;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByProject_Id(Long projectId);

    List<Vote> findAllByProject_Id(Long projectId);
    boolean existsByProjectIdAndVoterId(Long projectId, Long VoterId);
}
