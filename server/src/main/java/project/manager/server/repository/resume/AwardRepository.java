package project.manager.server.repository.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.resume.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    @EntityGraph(attributePaths = {"awardImage"})
    Optional<Award> findById(Long awardId);
    @EntityGraph(attributePaths = {"awardImage"})
    List<Award> findByResumeId(Long resumeId);
}
