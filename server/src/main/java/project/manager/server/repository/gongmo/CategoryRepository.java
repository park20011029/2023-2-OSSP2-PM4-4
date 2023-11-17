package project.manager.server.repository.gongmo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.gongmo.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
}
