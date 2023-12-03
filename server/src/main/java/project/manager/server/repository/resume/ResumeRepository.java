package project.manager.server.repository.resume;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.resume.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r " +
            "JOIN FETCH r.gu g " +
            "JOIN FETCH g.si s " +
            "WHERE r.id = :resumeId")
    Optional<Resume> findById(@Param("resumeId") Long resumeId);
    Optional<Resume> findByUserId(Long UserId);

    boolean existsByUserId(Long id);
}
