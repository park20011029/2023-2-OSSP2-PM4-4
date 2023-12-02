package project.manager.server.repository.post.contest;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.contest.ContestPost;

@Repository
public interface ContestPostRepository extends JpaRepository<ContestPost, Long> {
    Optional<ContestPost> findById(Long contestPostId);

    @Query(value = "SELECT c FROM ContestPost c " +
            "JOIN FETCH c.writer WHERE c.isDelete = false " +
            "ORDER BY CASE WHEN (c.startAt <= :today  AND c.endAt >= :today) " +
            "THEN 1 ELSE 0 END DESC , c.startAt DESC")
    Page<ContestPost> findAllWithUser(@Param("today") LocalDate today, Pageable pageInfo);

    @Query(value = "SELECT c FROM ContestPost c JOIN FETCH c.writer w " +
            "WHERE w.id = :userId AND c.isDelete = false " +
            "ORDER BY CASE WHEN (c.startAt <= :today AND c.endAt >= :today) " +
            "THEN 1 ELSE 0 END DESC, c.startAt DESC")
    Page<ContestPost> findByWriter(
            @Param("today") LocalDate today,
            @Param("userId") Long userId,
            Pageable pageInfo);
}
