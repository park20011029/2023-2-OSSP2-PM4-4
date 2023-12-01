package project.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
