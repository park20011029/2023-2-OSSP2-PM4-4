package project.manager.server.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickName(String nickName);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

    boolean existsByIdNotAndNickName(Long id, String nickName);
}
