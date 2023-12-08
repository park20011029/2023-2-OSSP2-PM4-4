package project.manager.server.repository.region;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.manager.server.domain.region.Gu;

public interface GuRepository extends JpaRepository<Gu, Long> {
    Optional<List<Gu>> findGuBySi_Id(Long siId);

    Optional<Gu> findGuById(Long guId);
}
