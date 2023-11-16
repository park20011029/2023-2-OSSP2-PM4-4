package project.manager.server.repository.resume;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.resume.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findById(Long resumeId);

    boolean existsByUserId(Long id);
}
