package project.manager.server.dto.reponse.building;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.building.Part;

@Getter
@Builder
public class PartDto {
    private String partName;
    private Long partId;
}
