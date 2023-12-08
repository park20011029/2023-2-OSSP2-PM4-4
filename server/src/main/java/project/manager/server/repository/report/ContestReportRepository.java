package project.manager.server.repository.report;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.report.ContestReport;

@Repository
public interface ContestReportRepository extends JpaRepository<ContestReport, Long> {

    @EntityGraph(attributePaths = {"defendant", "reporter", "reportedContest"})
    Page<ContestReport> findAll(Pageable pageInfo);

    @Query("SELECT cr FROM ContestReport cr " +
            "JOIN FETCH cr.defendant d " +
            "WHERE cr.id = :reportId")
    Optional<ContestReport> findByIdWithDefendant(@Param("reportId") Long reportId);

}
