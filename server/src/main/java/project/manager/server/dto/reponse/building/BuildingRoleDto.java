package project.manager.server.dto.reponse.building;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.User;
import project.manager.server.domain.building.BuildRoleMap;

import java.util.List;

@Getter
@NoArgsConstructor
public class BuildingRoleDto {
    private Integer total;
    private Integer headcount;
    private Boolean block;
    private List<RoleDto> role;
    private String userName;

    @Builder
    public BuildingRoleDto(BuildRoleMap buildRoleMap, User user, List<RoleDto> role){
        this.total = buildRoleMap.getTotal();
        this.headcount = buildRoleMap.getHeadCount();
        this.block = buildRoleMap.getBlock();
        this.userName = user.getNickName();
        this.role = role;

    }
}
