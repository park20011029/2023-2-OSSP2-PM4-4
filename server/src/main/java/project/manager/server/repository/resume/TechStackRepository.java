package project.manager.server.repository.resume;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.resume.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    Optional<List<TechStack>> findByResumeId(Long resumeId);
}
