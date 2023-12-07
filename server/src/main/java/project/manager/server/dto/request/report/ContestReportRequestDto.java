package project.manager.server.dto.request.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.ReportReason;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContestReportRequestDto {
    private ReportReason reportReason;
    private String description;
    private Long reporterId;
    private Long defendantId;
    private Long contestPostId;
}
