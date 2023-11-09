package project.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.ResumeAward;

@Repository
public interface ResumeAwardRepository extends JpaRepository<ResumeAward, Long> {
}
