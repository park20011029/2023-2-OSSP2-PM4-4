package project.manager.server.repository.post.contast;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.post.contest.Scale;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long> {
    List<Scale> findAll();
    Optional<Scale> findById(Long scaleId);
}
