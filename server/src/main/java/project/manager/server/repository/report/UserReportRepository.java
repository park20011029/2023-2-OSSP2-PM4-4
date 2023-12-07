package project.manager.server.repository.report;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.report.UserReport;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    @EntityGraph(attributePaths = {"defendant", "reporter"})
    Page<UserReport> findAll(Pageable pageInfo);

    @Query("SELECT ur FROM UserReport ur " +
            "JOIN FETCH ur.defendant d " +
            "WHERE ur.id = :reportId")
    Optional<UserReport> findByIdWithDefendant(@Param("reportId") Long reportId);
}
