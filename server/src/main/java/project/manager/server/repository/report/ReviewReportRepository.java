package project.manager.server.repository.report;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.report.ReviewReport;

@Repository
public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {

    @EntityGraph(attributePaths = {"defendant", "reporter", "reportedReview"})
    Page<ReviewReport> findAll(Pageable pageInfo);

    @Query("SELECT rr FROM ReviewReport rr " +
            "JOIN FETCH rr.reportedReview " +
            "JOIN FETCH rr.defendant d " +
            "WHERE rr.id = :reportId")
    Optional<ReviewReport> findById(@Param("reportId") Long reportId);

    @Query("SELECT rr FROM ReviewReport rr " +
            "JOIN FETCH rr.defendant d " +
            "WHERE rr.id = :reportId")
    Optional<ReviewReport> findByIdWithDefendant(@Param("reportId") Long reportId);
}
