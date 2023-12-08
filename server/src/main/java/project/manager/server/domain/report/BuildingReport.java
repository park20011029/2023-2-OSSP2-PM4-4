package project.manager.server.domain.report;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.enums.ReportReason;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BUILDING_REPORT_TB")
@DynamicUpdate
public class BuildingReport {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "create_at")
    private Timestamp createAt;

    // -------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defendant_id")
    private User defendant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingpost_id")
    private BuildingPost reportedBuilding;

    // -------------------------------------------------------------------

    @Builder
    public BuildingReport(ReportReason reportReason, String description, User reporter, User defendant, BuildingPost reportedBuilding) {
        this.reportReason = reportReason;
        this.description = description;
        this.reporter = reporter;
        this.defendant = defendant;
        this.reportedBuilding = reportedBuilding;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
