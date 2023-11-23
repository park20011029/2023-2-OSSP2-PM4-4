package project.manager.server.repository.post.contast;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.post.contest.Category;
import project.manager.server.domain.post.contest.ContestPost;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContestPostRepository extends JpaRepository<ContestPost, Long> {
    Optional<ContestPost> findById(Long contestPostId);

    @Query("SELECT c FROM ContestPost c JOIN FETCH c.user ORDER BY (c.startAt <= ?1 AND c.endAt >= ?1) DESC , c.startAt DESC")
    Page<ContestPost> findAllWithUser(LocalDate today, Pageable pageInfo);
}
