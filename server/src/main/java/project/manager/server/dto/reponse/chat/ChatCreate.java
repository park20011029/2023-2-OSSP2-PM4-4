package project.manager.server.dto.reponse.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChatCreate {
    private Long chatRoomId;
    private Long recevierId;
    private Long postWrieterId;
}
