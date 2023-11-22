package project.manager.server.dto.reponse.post.contest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.post.contest.Benefit;

@Getter
@NoArgsConstructor
public class BenefitDto {
    private Long id;
    private String benefit;

    @Builder
    public BenefitDto(Benefit benefit) {
        this.id = benefit.getId();
        this.benefit = benefit.getBenefit();
    }
}
