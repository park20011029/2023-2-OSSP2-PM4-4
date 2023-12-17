package project.manager.server.dto.reponse.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomDto {
    private String opponentNickname;
    private Long chatRoomId;
    private String lastChat;
}
