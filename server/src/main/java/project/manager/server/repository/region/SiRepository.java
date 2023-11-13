package project.manager.server.repository.region;

import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.region.Si;

import java.util.List;

public interface SiRepository extends JpaRepository<Si, Long> {
    List<Si> findAll();
}
