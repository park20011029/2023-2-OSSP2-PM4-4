package project.manager.server.repository.post.contast;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.post.contest.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
    List<Target> findAll();
    Optional<Target> findById(Long targetId);
}
