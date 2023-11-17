package project.manager.server.repository.gongmo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.gongmo.Target;

public interface TargetRepository extends JpaRepository<Target, Long> {
    List<Target> findAll();
}
