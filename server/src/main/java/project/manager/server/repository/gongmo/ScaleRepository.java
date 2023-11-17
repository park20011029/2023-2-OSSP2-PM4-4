package project.manager.server.repository.gongmo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.gongmo.Scale;

public interface ScaleRepository extends JpaRepository<Scale, Long> {
    List<Scale> findAll();
}
