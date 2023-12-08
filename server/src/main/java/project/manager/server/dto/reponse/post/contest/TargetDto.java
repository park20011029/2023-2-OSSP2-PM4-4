package project.manager.server.dto.reponse.post.contest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.post.contest.Target;

@Getter
@NoArgsConstructor
public class TargetDto {

    private Long id;
    private String target;

    @Builder
    public TargetDto(Target target) {
        this.id = target.getId();
        this.target = target.getTarget();
    }
}
