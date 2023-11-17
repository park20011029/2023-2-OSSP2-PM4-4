package project.manager.server.dto.reponse;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.manager.server.domain.gongmo.GongMoPost;

@Getter
@AllArgsConstructor
@Builder
public class BoardDto {
    private List<GongMoPost> gongMoPostList;
}
