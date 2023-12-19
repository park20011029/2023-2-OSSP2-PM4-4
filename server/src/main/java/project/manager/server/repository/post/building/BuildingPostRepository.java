package project.manager.server.repository.post.building;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.building.Part;
import project.manager.server.domain.post.contest.ContestPost;

@Repository
public interface BuildingPostRepository extends JpaRepository<BuildingPost, Long> {
    Optional<BuildingPost> findById(Long buildingPostId);

    //팀 빌딩 게시글 수정, 마감
    @Query("SELECT b FROM BuildingPost b " +
            "JOIN FETCH b.writer " +
            "WHERE b.id = :buildingPostId AND b.isRecruiting = true AND b.contestPost IS NOT NULL ")
    Optional<BuildingPost> findBuildingPostByIdAndRecruiting(@Param("buildingPostId") Long buildingPostId);

    //프로젝트 게시글 게시글 수정, 마감
    @Query("SELECT b FROM BuildingPost b " +
            "JOIN FETCH b.writer " +
            "WHERE b.id = :buildingPostId AND b.isRecruiting = true AND b.contestPost IS NULL ")
    Optional<BuildingPost> findProjectPostByIdAndRecruiting(@Param("buildingPostId") Long buildingPostId);

    //삭제
    @Query("SELECT bp FROM BuildingPost bp " +
            "JOIN FETCH bp.writer " +
            "WHERE bp.id = :buildingPostId AND bp.isDelete = false")
    Optional<BuildingPost> findByIdAndIsDeleteFalse(@Param("buildingPostId") Long buildingPostId);

    //팀빌딩 게시글 리스트
    @Query("SELECT b FROM BuildingPost b " +
            "JOIN FETCH b.writer " +
            "WHERE b.contestPost = :contestPost AND b.isDelete = false ")
    Page<BuildingPost> findByContestPostWithUser(@Param("contestPost") ContestPost contestPost, Pageable pageInfo);

    //포로젝트 게시글 리스트
    @Query("SELECT b FROM BuildingPost b " +
            "JOIN FETCH b.writer " +
            "WHERE b.contestPost IS NULL AND b.isDelete = false")
    Page<BuildingPost> findProjectPostWithUser(Pageable pageInfo);

    //유저 팀빌딩 게시글 목록
    @Query("SELECT b FROM BuildingPost b " +
            "JOIN FETCH b.writer " +
            "WHERE b.writer.id = :userId AND b.isDelete = false And b.contestPost IS NOT NULL")
    Page<BuildingPost> findBuildingPostByWithUser(@Param("userId") Long userId, Pageable pageInfo);

    //유저 프로젝트 게시글 목록
    @Query("SELECT b FROM BuildingPost b " +
            "JOIN FETCH b.writer " +
            "WHERE b.writer.id = :userId AND b.isDelete = false And b.contestPost IS NULL")
    Page<BuildingPost> findProjectPostByWithUser(@Param("userId") Long userId, Pageable pageInfo);

    //검색어로 프로젝트 게시글 검색
    @Query(value = "SELECT * FROM building_post_tb b " +
            "WHERE (b.content LIKE %:text% OR b.title LIKE %:text%) AND b.contestpost_id IS NULL", nativeQuery = true)
    Page<BuildingPost> findProjectPostByText(@Param("text") String text, Pageable pageInfo);

}
