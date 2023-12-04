package project.manager.server.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**내가 작성한 리뷰 목록 */
    @Query("SELECT r FROM Review r JOIN FETCH r.reviewer " +
            "JOIN FETCH r.buildingPost " +
            "WHERE r.reviewer.id = :userId AND r.content IS NOT NULL")
    Page<Review> findReviewsByReviewerId(@Param("userId") Long userID, Pageable pageable);

    /** 내가 작성해야 할 리뷰 목록 */
    @Query("SELECT r FROM Review r JOIN FETCH r.reviewer " +
            "JOIN FETCH r.buildingPost " +
            "WHERE r.reviewer.id = :userId AND r.content IS NULL")
    Page<Review> findBeforeReviewsByReviewerId(@Param("userId") Long userID, Pageable pageable);

    /** 내가 받은 리뷰 목록 */
    @Query("SELECT r FROM Review r JOIN FETCH r.reviewee " +
            "JOIN FETCH r.content " +
            "WHERE r.reviewee.id = :userId AND r.content IS NOT NULL")
    Page<Review> findMyReviewsByReviweeId(@Param("userId") Long userID, Pageable pageable);

    /** 리뷰 작성 */
    @EntityGraph(attributePaths = {"reviewer"})
    Optional<Review> findByIdAndContentIsNull(Long reviewId);
}
