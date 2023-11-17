package project.manager.server.repository.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.building.BuildRoleMap;
import project.manager.server.domain.building.Role;

@Repository
public interface BuildingRoleMapRepository extends JpaRepository<BuildRoleMap, Long> {
}
