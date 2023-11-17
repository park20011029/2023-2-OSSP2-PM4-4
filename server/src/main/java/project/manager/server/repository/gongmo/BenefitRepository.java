package project.manager.server.repository.gongmo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.gongmo.Benefit;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    List<Benefit> findAll();
}
