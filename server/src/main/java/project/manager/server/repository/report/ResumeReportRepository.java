package project.manager.server.repository.report;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.report.ResumeReport;


@Repository
public interface ResumeReportRepository extends JpaRepository<ResumeReport, Long> {

    @EntityGraph(attributePaths = {"defendant", "reporter", "reportedResume"})
    Page<ResumeReport> findAll(Pageable pageInfo);

    @Query("SELECT rr FROM ResumeReport rr " +
            "JOIN FETCH rr.defendant d " +
            "WHERE rr.id = :reportId")
    Optional<ResumeReport> findByIdWithDefendant(@Param("reportId") Long reportId);
}
