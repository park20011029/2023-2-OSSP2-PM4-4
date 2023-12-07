package project.manager.server.dto.request.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.ReportReason;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeReportRequestDto {
    private Long resumeId;
    private Long reporterId;
    private Long defendantId;
    private String description;
    private ReportReason reportReason;
}
