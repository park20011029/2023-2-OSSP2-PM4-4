package project.manager.server.repository.post.contast;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.post.contest.Benefit;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    List<Benefit> findAll();
    Optional<Benefit> findById(Long benefitId);
}
