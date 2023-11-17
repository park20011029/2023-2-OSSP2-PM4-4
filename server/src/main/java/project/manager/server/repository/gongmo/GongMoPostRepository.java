package project.manager.server.repository.gongmo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.gongmo.GongMoPost;

public interface GongMoPostRepository extends JpaRepository<GongMoPost, Long> {

    List<GongMoPost> findByBoardId(Long boardId);

    boolean existsByBoardId(Long id);
}
