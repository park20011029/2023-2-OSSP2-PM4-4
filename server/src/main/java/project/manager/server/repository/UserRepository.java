package project.manager.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.User;
import project.manager.server.enums.UserRole;

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

    Optional<User> findByEmail(String email);


    @Query(value = "SELECT u.id AS id, u.role AS userRole FROM User u WHERE u.id = :userId")
    Optional<UserLoginForm> findUserForAuthentication(@Param("userId") Long userId);

    @Query("SELECT u.id AS id, u.role AS userRole FROM User u WHERE u.id = :userId AND u.isLogin = true AND u.refreshToken = :refreshToken")
    Optional<UserLoginForm> findByIdAndRefreshToken(@Param("userId") Long userId, @Param("refreshToken") String refreshToken);

    Optional<User> findByIdAndIsLoginAndRefreshTokenIsNotNull(Long userId, Boolean isLogin);

    public interface UserLoginForm {
        Long getId();
        UserRole getUserRole();
    }
}
