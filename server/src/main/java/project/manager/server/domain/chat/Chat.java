package project.manager.server.domain.chat;


import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;

import project.manager.server.domain.User;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue
    @Column
    //채팅 아이디
    private int id;

    @Column
    private String content;

    //채팅 친 시간
    @CreationTimestamp
    @Column
    private LocalDateTime sendDate;

    //-------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User sender;

    @Builder
    public Chat(ChatRoom chatRoomId, String content, User sender ){
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.sender = sender;
    }

}
