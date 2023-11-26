package project.manager.server.dto.reponse.post.contest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.post.contest.Scale;

@Getter
@NoArgsConstructor
public class ScaleDto {

    private Long id;
    private String scale;

    @Builder
    public ScaleDto(Scale scale) {
        this.id = scale.getId();
        this.scale = scale.getScale();
    }
}
