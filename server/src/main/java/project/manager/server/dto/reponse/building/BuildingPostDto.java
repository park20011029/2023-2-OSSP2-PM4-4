package project.manager.server.dto.reponse.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.manager.server.domain.User;
import project.manager.server.domain.building.BuildingPost;

import java.sql.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class BuildingPostDto {
    private Long buildingPostId;
    private String writer;
    private String title;
    private String content;
    private Timestamp createAt;
    private List<BuildingRoleDto> buildRole;

    @Builder
    public BuildingPostDto(BuildingPost buildingPost, List<BuildingRoleDto> buildRole, User user ){
        this.buildingPostId = buildingPost.getId();
        this.writer = user.getNickName();
        this.title = buildingPost.getTitle();
        this.content = buildingPost.getContent();
        this.createAt = buildingPost.getCreateAt();
        this.buildRole = buildRole;
    }
}
