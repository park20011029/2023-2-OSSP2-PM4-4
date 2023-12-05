package project.manager.server.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
    private Long reviewId;
    private String content;
    private Long reviewerId;
    private String reviewer;
    private Long revieweeId;
    private String reviewee;
    private Double score;
    private String projectName;
    private Long projectId;
}
