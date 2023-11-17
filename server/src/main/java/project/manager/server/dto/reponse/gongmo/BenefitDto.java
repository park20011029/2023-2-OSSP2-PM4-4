package project.manager.server.dto.reponse.gongmo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Benefit;

@Getter
@NoArgsConstructor
public class BenefitDto {
    Benefit benefit;

    @Builder
    public BenefitDto(Benefit benefit) {
        this.benefit = benefit;
    }
}
