package project.manager.server.dto.request.gongmo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Benefit;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BenfitRequestDto {
    private Benefit benefit;
}
