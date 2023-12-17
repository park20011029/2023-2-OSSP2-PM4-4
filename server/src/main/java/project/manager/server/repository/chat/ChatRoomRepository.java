package project.manager.server.repository.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.manager.server.domain.User;
import project.manager.server.domain.chat.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long id);
    List<ChatRoom> findByReceiverIdOrPostWriterId(User receiverId, User postWriterId);

}
