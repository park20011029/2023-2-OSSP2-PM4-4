package project.manager.server.domain.report;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;
import project.manager.server.domain.resume.Resume;
import project.manager.server.enums.ReportReason;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESUME_REPORT_TB")
@DynamicUpdate
public class ResumeReport {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
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
    @JoinColumn(name = "resume_id")
    private Resume reportedResume;

    // -------------------------------------------------------------------

    @Builder
    public ResumeReport(ReportReason reportReason, String description, User reporter, User defendant, Resume reportedResume) {
        this.reportReason = reportReason;
        this.description = description;
        this.reporter = reporter;
        this.defendant = defendant;
        this.reportedResume = reportedResume;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
