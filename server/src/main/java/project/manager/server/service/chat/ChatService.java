package project.manager.server.service.chat;



import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.chat.Chat;
import project.manager.server.domain.chat.ChatRoom;
import project.manager.server.dto.reponse.chat.ChatDto;
import project.manager.server.dto.request.chat.ChatRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.chat.ChatRepository;
import project.manager.server.repository.chat.ChatRoomRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Boolean save(ChatRequestDto chatRequestDto){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRequestDto.getChatRoomId())
                .orElseThrow(()-> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        User user = userRepository.findById(chatRequestDto.getSender())
                .orElseThrow(()-> new ApiException(ErrorDefine.USER_NOT_FOUND));


        Chat chat = Chat.builder()
                .chatRoomId(chatRoom)
                .sender(user)
                .content(chatRequestDto.getContent())
                .build();

        chatRepository.save(chat);
        return true;
    }

    //채팅방 내용 불러오기
    public Map<String, Object> findAllChatByRoomId(Long id){

        ChatRoom chatRoom = chatRoomRepository.findById(id)
                .orElseThrow(()-> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
            System.err.println("save에서 chatservice chatroom 조회");
        Map<String, Object> result = new HashMap<>();

        result.put("ChatLog", chatRepository.findByChatRoomId(chatRoom)
                .stream().map(chat1 ->
                        ChatDto.builder()
                                .chatRoomId(chatRoom.getId())
                                .sender(chat1.getSender().getId())
                                .sendDate(chat1.getSendDate())
                                .content(chat1.getContent())
                                .build())
                .collect(Collectors.toList()));

       /* result.put("chat", ChatDto.builder()
                .chatRoomId(chat.getChatRoomId().getId())
                .sender(chat.getSender())
                .content(chat.getContent())
                .sendDate(chat.getSendDate())
                .build());*/

        return result;
    }
}
