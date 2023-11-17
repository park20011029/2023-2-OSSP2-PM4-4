package project.manager.server.dto.request.gongmo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GongCaMapRequestDto {

    private Long benefitId;
    private Long categoryId;
    private Long organizationId;
    private Long scaleId;
    private Long targetId;
}
