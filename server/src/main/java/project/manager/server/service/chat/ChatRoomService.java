package project.manager.server.service.chat;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.chat.ChatRoom;
import project.manager.server.dto.request.ChatRoomRequestDto;
import project.manager.server.repository.chat.ChatRoomRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Long createChatRoom(ChatRoomRequestDto chatRoomRequestDto){
         ChatRoom newChatRoom = ChatRoom.builder()
                .roomName(chatRoomRequestDto.getRoomName())
                .postWriterId(chatRoomRequestDto.getPostWriterId())
                .userId(chatRoomRequestDto.getUserId())
                .build();

        chatRoomRepository.save(newChatRoom);

        return newChatRoom.getId();
    }
}
