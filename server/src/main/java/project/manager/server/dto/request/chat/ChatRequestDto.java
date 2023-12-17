package project.manager.server.dto.request.chat;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {
    @NotNull
    private Long chatRoomId;

    @NotNull
    private Long sender;

    @NotNull
    private String content;

}
