package project.manager.server.repository.region;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.manager.server.domain.region.Si;

public interface SiRepository extends JpaRepository<Si, Long> {
    List<Si> findAll();
}
