package project.manager.server.repository.post.building;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.ApplyTechType;

@Repository
public interface ApplyTechTypeRepository extends JpaRepository<ApplyTechType, Long> {
    Optional<ApplyTechType> findById(Long applyTechTypeId);
}
