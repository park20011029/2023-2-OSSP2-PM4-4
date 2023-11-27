package project.manager.server.repository.post.building;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.contest.ContestPost;

@Repository
public interface BuildingPostRepository extends JpaRepository<BuildingPost, Long> {
    Optional<BuildingPost> findById(Long buildingPostId);

    @Query("SELECT bp FROM BuildingPost bp JOIN FETCH bp.writer WHERE bp.id = :buildingPostId AND bp.isDelete = false")
    Optional<BuildingPost> findByIdAndIsDeleteFalse(Long buildingPostId);

    @Query("SELECT b FROM BuildingPost b JOIN FETCH b.writer WHERE b.contestPost = :contestPost AND b.isDelete = false")
    Page<BuildingPost> findByContestPostWithUser(ContestPost contestPost, Pageable pageInfo);

}
