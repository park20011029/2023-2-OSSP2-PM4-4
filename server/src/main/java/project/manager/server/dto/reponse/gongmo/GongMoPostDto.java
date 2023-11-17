package project.manager.server.dto.reponse.gongmo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GongMoPostDto {

    private final Long id;
    private final String title;
    private final String content;
}
