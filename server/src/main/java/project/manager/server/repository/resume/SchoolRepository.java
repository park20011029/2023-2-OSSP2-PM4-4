package project.manager.server.repository.resume;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import project.manager.server.domain.resume.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
    @EntityGraph(attributePaths = {"schoolImage"})
    Optional<School> findById(Long schoolId);

    @EntityGraph(attributePaths = {"schoolImage"})
    Optional<School> findByResumeId(Long resumeId);
}
