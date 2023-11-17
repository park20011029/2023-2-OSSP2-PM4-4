package project.manager.server.dto.request.gongmo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GongMoPostRequestDto {
    private String nickname;
    private String boardName;
    private String title;
    private String content;
    private LocalDate startAt;
    private LocalDate endAt;
}
