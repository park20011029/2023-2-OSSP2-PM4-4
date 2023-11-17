package project.manager.server.dto.request.building;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingPostRequestDto {
    private Long userId;
    private String title;
    private String content;
    private Long gongMoPostId;

    private List<BuildingRoleRequestDto> buildingRole;
}
