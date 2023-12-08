package project.manager.server.domain.chat;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chatroom_tb")
@DynamicUpdate
public class ChatRoom {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "postwriter_Id", nullable = false)
    private Long postWriterId;

    @Column(name = "room_name")
    private String roomName;

    @Builder
    public ChatRoom(Long userId, Long postWriterId, String roomName) {
        this.userId = userId;
        this.postWriterId = postWriterId;
        this.roomName = roomName;
    }
}
