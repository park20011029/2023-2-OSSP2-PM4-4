package project.manager.server.dto.reponse.gongmo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.manager.server.domain.gongmo.GongCaMap;

@Getter
@AllArgsConstructor
public class GongCaMapDto {

    private Long gongCaMapId;
    private CategoryDto category;
    private BenefitDto benefit;
    private OrganizationDto organization;
    private ScaleDto scale;
    private TargetDto target;

    // -----------------------------------------------------
    public GongCaMapDto(GongCaMap gongCaMap) {
        this.gongCaMapId = gongCaMap.getId();
        this.category = CategoryDto.builder().category(gongCaMap.getCategory()).build();
        this.benefit = BenefitDto.builder().benefit(gongCaMap.getBenefit()).build();
        this.organization =
                OrganizationDto.builder().organization(gongCaMap.getOrganization()).build();
        this.scale = ScaleDto.builder().scale(gongCaMap.getScale()).build();
        this.target = TargetDto.builder().target(gongCaMap.getTarget()).build();
    }
}
