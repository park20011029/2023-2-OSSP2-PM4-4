package project.manager.server.service.chat;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.chat.Chat;
import project.manager.server.repository.chat.ChatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Chat save(Chat chat){
        //여기서 실제로 있는 채팅방인가에 대해서 Check 해줘야 하고 채팅 저장.
        chatRepository.save(chat);
        return chat;
    }

    //채팅방 내용 불러오기
    public List<Chat> findAllChatByRoomId(Long id){
        return chatRepository.findAllByChatRoomId(id);
    }
}
