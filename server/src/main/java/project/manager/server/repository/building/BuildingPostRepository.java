package project.manager.server.repository.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.building.BuildingPost;
import project.manager.server.domain.building.Role;
import project.manager.server.domain.gongmo.GongMoPost;

import java.util.List;

@Repository
public interface BuildingPostRepository extends JpaRepository<BuildingPost, Long> {

    boolean existsByUserId(Long id);
    /*List<BuildingPost> findByBoardId(Long gongMoPostId);*/
}
