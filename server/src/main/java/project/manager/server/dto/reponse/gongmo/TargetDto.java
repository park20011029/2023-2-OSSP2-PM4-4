package project.manager.server.dto.reponse.gongmo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Target;

@Getter
@NoArgsConstructor
public class TargetDto {
    Target target;

    @Builder
    public TargetDto(Target target) {
        this.target = target;
    }
}
