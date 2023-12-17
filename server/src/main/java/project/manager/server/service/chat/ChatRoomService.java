package project.manager.server.service.chat;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.chat.Chat;
import project.manager.server.domain.chat.ChatRoom;
import project.manager.server.dto.reponse.chat.ChatDto;
import project.manager.server.dto.reponse.chat.ChatRoomDto;
import project.manager.server.dto.request.chat.ChatRoomRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.chat.ChatRepository;
import project.manager.server.repository.chat.ChatRoomRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public Long createChatRoom(ChatRoomRequestDto chatRoomRequestDto){

        User user = userRepository.findById(chatRoomRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        User userPostWriter = userRepository.findById(chatRoomRequestDto.getPostWriterId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        ChatRoom newChatRoom = ChatRoom.builder()
                .postWriterId(userPostWriter)
                .user(user)
                .build();

        chatRoomRepository.save(newChatRoom);

        return newChatRoom.getId();
    }

    public Map<String, Object> readChatList(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ApiException(ErrorDefine.USER_NOT_FOUND));

        System.err.println(" chatroomservice chatroom 조회");
        Map<String, Object> result = new HashMap<>();
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();

        chatRoomRepository.findByReceiverIdOrPostWriterId(user, user)
                .forEach(chatRoom -> {
                    Chat lastChat = chatRepository.findByChatRoomId(chatRoom)
                            .stream()
                            .sorted(Comparator.comparing(Chat::getSendDate).reversed())
                            .findFirst()
                            .orElse(null);

                    chatRoomDtoList.add(ChatRoomDto.builder()
                            .opponentNickname(
                                    chatRoom.getPostWriterId().getId().equals(userId)
                                            ? chatRoom.getReceiverId().getNickName()
                                            : chatRoom.getPostWriterId().getNickName()
                            )
                            .chatRoomId(chatRoom.getId())
                            .lastChat(lastChat != null ? lastChat.getContent() : null)
                            .build());
                });

        result.put("ChatRoomList", chatRoomDtoList);

        return result;
    }


}
