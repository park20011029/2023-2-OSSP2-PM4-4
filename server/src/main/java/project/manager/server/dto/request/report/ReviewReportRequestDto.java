package project.manager.server.dto.request.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.ReportReason;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReportRequestDto {
    private Long reviewId;
    private Long reporterId;
    private Long defendantId;
    private String description;
    private ReportReason reportReason;
}
