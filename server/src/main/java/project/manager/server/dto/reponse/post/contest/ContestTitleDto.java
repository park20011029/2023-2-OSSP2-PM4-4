package project.manager.server.dto.reponse.post.contest;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContestTitleDto {
    private Long contestId;
    private String title;
    private Long userId;
    private String user;
    private LocalDate startAt;
    private LocalDate endAt;
    private String imageUrl;
}
