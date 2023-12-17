package project.manager.server.repository.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.chat.Chat;
import project.manager.server.domain.chat.ChatRoom;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query("SELECT c FROM Chat c WHERE c.chatRoomId = :chatRoom")
    List<Chat> findByChatRoomId(@Param("chatRoom") ChatRoom chatRoom);
}
