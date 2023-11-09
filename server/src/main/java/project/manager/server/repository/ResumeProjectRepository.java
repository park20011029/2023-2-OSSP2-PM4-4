package project.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.ResumeProject;

@Repository
public interface ResumeProjectRepository extends JpaRepository<ResumeProject, Long> {
}
