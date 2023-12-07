package project.manager.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickName(String nickName);

    Optional<User> findById(Long userId);

    @EntityGraph(attributePaths = "userImage")
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByUserWithImage(@Param("userId") Long userId);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

    boolean existsByIdNotAndNickName(Long id, String nickName);
}
