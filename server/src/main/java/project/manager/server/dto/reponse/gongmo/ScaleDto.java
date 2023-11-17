package project.manager.server.dto.reponse.gongmo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Scale;

@Getter
@NoArgsConstructor
public class ScaleDto {
    Scale scale;

    @Builder
    public ScaleDto(Scale scale) {
        this.scale = scale;
    }
}
