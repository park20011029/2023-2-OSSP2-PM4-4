package project.manager.server.dto.reponse.gongmo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GongCaListDto {
    private List<BenefitDto> benefit;
    private List<CategoryDto> category;
    private List<OrganizationDto> organization;
    private List<ScaleDto> scale;
    private List<TargetDto> target;
}
