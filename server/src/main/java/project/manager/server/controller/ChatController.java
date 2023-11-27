package project.manager.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import project.manager.server.domain.chat.Chat;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.ChatRoomRequestDto;
import project.manager.server.service.chat.ChatRoomService;
import project.manager.server.service.chat.ChatService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/{chatRoomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{chatRoomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public Chat chat(Message<Chat> m) {
        System.err.println(m.toString());
        Chat chat = Chat.builder()
                .chatRoomId(m.getPayload().getChatRoomId())
                .sender(m.getPayload().getSender())
                .content(m.getPayload().getContent())
                .build();

        return chatService.save(chat);
    }

    @GetMapping("/chatting/{chatRoomId}")
    public ResponseDto<List<Chat>> loadChat(@PathVariable Long chatRoomId){
        System.err.println("get method1");
        return new ResponseDto<>(chatService.findAllChatByRoomId(chatRoomId));
    }

    @PostMapping("/chatroom")
    public ResponseDto<Long> createChatRoom(
            @Valid @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        System.err.println(chatRoomRequestDto.getRoomName()+"  "+chatRoomRequestDto.getUserId()+"   "+chatRoomRequestDto.getPostWriterId());
        return new ResponseDto<Long>(chatRoomService.createChatRoom(chatRoomRequestDto));
    }
}
