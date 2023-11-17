package project.manager.server.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardName(String boardName);
}
