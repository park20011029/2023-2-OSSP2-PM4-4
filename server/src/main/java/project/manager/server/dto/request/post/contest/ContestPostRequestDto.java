package project.manager.server.dto.request.post.contest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContestPostRequestDto {
    @NotNull
    private String content;
    @NotNull
    private String title;
    @NotNull
    private LocalDate startAt;
    @NotNull
    private LocalDate endAt;
    @NotNull
    private Long userId;
    @NotNull
    private Long benefitId;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long organizationId;
    @NotNull
    private Long scaleId;
    @NotNull
    private Long targetId;

}
