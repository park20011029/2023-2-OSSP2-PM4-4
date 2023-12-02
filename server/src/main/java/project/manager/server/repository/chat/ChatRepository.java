package project.manager.server.repository.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.chat.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {
    List<Chat> findAllByChatRoomId(Long chatRoomId);
}
