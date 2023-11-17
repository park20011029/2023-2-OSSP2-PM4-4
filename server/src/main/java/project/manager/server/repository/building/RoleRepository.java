package project.manager.server.repository.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.building.Role;
import project.manager.server.domain.region.Gu;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<List<Role>> findRoleBypartId(Long partId);

    Optional<Role> findRoleById(Long Role);
}
