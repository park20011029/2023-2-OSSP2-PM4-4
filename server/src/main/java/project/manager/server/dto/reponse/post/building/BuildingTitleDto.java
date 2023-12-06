package project.manager.server.dto.reponse.post.building;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuildingTitleDto {
    private Long postId;
    private String title;
    private Long userId;
    private String user;
    private LocalDate creatAt;
}
