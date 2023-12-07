package project.manager.server.repository.report;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.report.BuildingReport;

@Repository
public interface BuildingReportRepository extends JpaRepository<BuildingReport, Long> {

    @EntityGraph(attributePaths = {"defendant", "reporter", "reportedBuilding"})
    Page<BuildingReport> findAll(Pageable pageInfo);

    @Query("SELECT br FROM BuildingReport br " +
            "JOIN FETCH br.defendant d " +
            "WHERE br.id = :reportId")
    Optional<BuildingReport> findByIdWithDefendant(@Param("reportId") Long reportId);
}
