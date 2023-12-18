package project.manager.server.repository.post.building;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.building.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findById(Long partId);
    List<Part> findByBuildingPost(BuildingPost buildingPost);

    @Query("SELECT p FROM Part p " +
            "JOIN FETCH p.buildingPost bp " +
            "JOIN FETCH bp.writer " +
            "WHERE p.id = :partId")
    Optional<Part> findByIdWithPostAndUser(@Param("partId") Long partId);

}
