package project.manager.server.domain.chat;

import jakarta.persistence.*;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;

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

   //-------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User receiverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_writer_id")
    private User postWriterId;

    @OneToMany(mappedBy = "chatRoomId", fetch = FetchType.LAZY)
    private List<Chat> chatList;

    @Builder
    public ChatRoom(User user, User postWriterId) {
        this.receiverId = user;
        this.postWriterId = postWriterId;
    }
}
