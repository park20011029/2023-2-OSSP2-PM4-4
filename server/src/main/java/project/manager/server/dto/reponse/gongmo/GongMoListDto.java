package project.manager.server.dto.reponse.gongmo;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GongMoListDto {
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private Long boardId;

    @Builder
    GongMoListDto(String title, LocalDate startAt, LocalDate endAt, Long boardId) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.boardId = boardId;
    }
}
