package project.manager.server.repository.post.building;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.BuildingPost;

@Repository
public interface BuildingPostRepository extends JpaRepository<BuildingPost, Long> {
    Optional<BuildingPost> findById(Long buildingPostId);
}
