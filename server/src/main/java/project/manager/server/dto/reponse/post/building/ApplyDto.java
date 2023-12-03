package project.manager.server.dto.reponse.post.building;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyDto {
    private Long applyId;
    private String state;
    private String partName;
    private String buildingPost;
    private Long buildingPostId;
}
