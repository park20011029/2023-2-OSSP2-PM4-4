package project.manager.server.dto.reponse.post.contest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.post.contest.Organization;

@Getter
@NoArgsConstructor
public class OrganizationDto {

    private Long id;
    private String organization;

    @Builder
    public OrganizationDto(Organization organization) {
        this.id = organization.getId();
        this.organization = organization.getOrganization();
    }
}
