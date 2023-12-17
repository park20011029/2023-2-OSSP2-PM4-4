package project.manager.server.dto.reponse.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class ChatDto {
    private Long chatRoomId;
    private Long sender;
    private String content;
    private LocalDateTime sendDate;

}
