package project.manager.server.dto.request.report;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.ReportReason;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingReportRequestDto {
    @NotNull
    private ReportReason reportReason;
    @NotNull
    private String description;
    @NotNull
    private Long reporterId;
    @NotNull
    private Long defendantId;
    @NotNull
    private Long buildingPostId;
}
