package project.manager.server.dto.reponse;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import project.manager.server.domain.chat.Chat;

@Getter
@AllArgsConstructor
public class ChatDto {
    private Long chatRoomId;
    private Long sender;
    private String content;
    private LocalDateTime sendDate;

    @Builder
    public ChatDto(Chat chat) {
        this.chatRoomId = chat.getChatRoomId();
        this.sender = chat.getSender();
        this.content = chat.getContent();
        this.sendDate = chat.getSendDate();
    }
}
