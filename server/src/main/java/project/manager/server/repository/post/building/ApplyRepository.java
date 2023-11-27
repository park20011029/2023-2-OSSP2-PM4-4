package project.manager.server.repository.post.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.Apply;

import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Optional<Apply> findById(Long applyId);

    @Query("SELECT a FROM Apply a JOIN FETCH a.applicant ap WHERE a.id = :applyId")
    Optional<Apply> findByIdWithApplicant(@Param("applyId") Long applyId);

}
