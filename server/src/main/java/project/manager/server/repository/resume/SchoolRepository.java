package project.manager.server.repository.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.resume.School;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByResumeId(Long resumeId);
}
