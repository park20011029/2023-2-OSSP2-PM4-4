package project.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.ResumeTech;

public interface ResumeTechRepository extends JpaRepository<ResumeTech, Long> {
}
