package project.manager.server.dto.reponse.post.contest;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ContestTitleDto {
    private Long contestId;
    private String title;
    private Long userId;
    private String user;
    private LocalDate startAt;
    private LocalDate endAt;
}
