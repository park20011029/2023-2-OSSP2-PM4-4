package project.manager.server.dto.request.building;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingRoleRequestDto {
    private Long buildingPost; // 빌딩게시글 아이디
    private Integer total; // 전체 수
    private Long roleId;
}
