package project.manager.server.repository.post.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
