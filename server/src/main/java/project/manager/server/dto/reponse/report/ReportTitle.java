package project.manager.server.dto.reponse.report;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ReportTitle {
    Long contentId;
    Long reportId;
    Long reporterId;
    String reportReason;
    String reporterNickName;
    Long defendantId;
    String defendantNickName;
    LocalDate createAt;
}
