package project.manager.server.dto.reponse.region;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.region.Gu;

@Getter
@NoArgsConstructor
public class GuDto {
    String name;
    Long guId;

    @Builder
    public GuDto(Gu gu) {
        this.name = gu.getGu();
        this.guId = gu.getId();
    }
}
