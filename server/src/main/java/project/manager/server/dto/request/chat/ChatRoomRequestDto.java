package project.manager.server.dto.request.chat;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequestDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long postWriterId;

}
