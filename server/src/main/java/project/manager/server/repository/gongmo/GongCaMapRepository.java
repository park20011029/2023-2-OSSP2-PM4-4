package project.manager.server.repository.gongmo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.manager.server.domain.gongmo.GongCaMap;

@Repository
public interface GongCaMapRepository extends JpaRepository<GongCaMap, Long> {

}
