package project.manager.server.repository.post.building;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.post.building.Apply;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Optional<Apply> findById(Long applyId);

    @Query("SELECT a FROM Apply a JOIN FETCH a.applicant ap WHERE a.id = :applyId")
    Optional<Apply> findByIdWithApplicant(@Param("applyId") Long applyId);

    @Query("SELECT a FROM Apply a " +
        "JOIN FETCH a.part p " +
        "JOIN FETCH p.buildingPost b " +
        "JOIN FETCH a.applicant " +
        "WHERE b.id = :buildingPostId AND b.isRecruiting = true ")
    List<Apply> findByPostIdWithPartAndApply(@Param("buildingPostId") Long buildingPostId);

    @Query("SELECT a FROM Apply a JOIN FETCH a.applicant u " +
            "JOIN FETCH a.part p " +
            "JOIN FETCH p.buildingPost b " +
            "WHERE u.id = :userId")
    Page<Apply> findByUserIdWithPartAndPost(
            @Param("userId") Long userId,
            Pageable pageable);

}
