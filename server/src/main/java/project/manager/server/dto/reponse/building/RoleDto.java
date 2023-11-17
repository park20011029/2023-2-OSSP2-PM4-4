package project.manager.server.dto.reponse.building;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.building.Part;
import project.manager.server.domain.building.Role;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoleDto {
    private Long partId;
    private String roleName;

    @Builder
    public RoleDto(Role role,List<PartDto> part){
        this.partId = role.getId();
        this.roleName = role.getRoleName();
    }
}
