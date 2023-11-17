package project.manager.server.dto.reponse.gongmo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Organization;

@Getter
@NoArgsConstructor
public class OrganizationDto {
    Organization organization;

    @Builder
    public OrganizationDto(Organization organization) {
        this.organization = organization;
    }
}
