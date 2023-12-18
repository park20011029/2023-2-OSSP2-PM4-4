package project.manager.server.controller;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.reponse.chat.ChatCreate;
import project.manager.server.dto.request.chat.ChatRequestDto;
import project.manager.server.dto.request.chat.ChatRoomRequestDto;
import project.manager.server.service.chat.ChatRoomService;
import project.manager.server.service.chat.ChatService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/{chatRoomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{chatRoomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ResponseDto<Boolean> chat(
            @Valid @RequestBody ChatRequestDto chatRequestDto
            ) {
        return new ResponseDto<>(chatService.save(chatRequestDto));
    }

    @GetMapping("/chatting/{chatRoomId}")
    public ResponseDto<Map<String, Object>> loadChat(@PathVariable Long chatRoomId){
        System.err.println("get method1");
        return new ResponseDto<>(chatService.findAllChatByRoomId(chatRoomId));
    }



    @PostMapping("/chatroom")
    public ResponseDto<ChatCreate> createChatRoom(
            @Valid @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        System.err.println("ddk");
        return new ResponseDto<>(chatRoomService.createChatRoom(chatRoomRequestDto));
    }

    @GetMapping("/chat/list/{userId}")
    public ResponseDto<Map<String,Object>> readChatList(
            @PathVariable Long userId
    ) {
        return new ResponseDto<>(chatRoomService.readChatList(userId));
    }
}
