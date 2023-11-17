package project.manager.server.dto.request.gongmo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Target;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TargetReqeustDto {
    private Target target;
}
