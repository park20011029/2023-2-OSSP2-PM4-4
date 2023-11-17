package project.manager.server.repository.gongmo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.manager.server.domain.gongmo.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findAll();
}
