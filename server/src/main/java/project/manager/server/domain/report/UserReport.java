package project.manager.server.domain.report;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;
import project.manager.server.enums.ReportReason;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_REPORT_TB")
@DynamicUpdate
public class UserReport {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    @Lob
    @Column(name = "description", nullable = false)
    private Long description;

    // -------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defendant_id")
    private User defendant;

    @Builder
    public UserReport(ReportReason reportReason, User reporter, User defendant) {
        this.reportReason = reportReason;
        this.reporter = reporter;
        this.defendant = defendant;
    }
}
