package project.manager.server.dto.reponse.region;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.region.Si;

@NoArgsConstructor
public class SiDto {
    private String si;
    private Long siId;

    @Builder
    public SiDto(Si si) {
        this.si = si.getSi();
        this.siId = si.getId();
    }
}
