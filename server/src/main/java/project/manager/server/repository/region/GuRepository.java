package project.manager.server.repository.region;

import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.region.Gu;

import java.util.List;
import java.util.Optional;

public interface GuRepository extends JpaRepository<Gu, Long> {
    Optional<List<Gu>> findGuBySi_Id(Long siId);
    Optional<Gu> findGuById(Long guId);
}
