package project.manager.server.domain.chat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Chat {
    @Id
    @GeneratedValue
    @Column
    //채팅 아이디
    private int id;

    //채팅방 아이디
    @Column
    private Long chatRoomId; // matching Id

    //보낸 사람
    @Column
    private Long sender;

    @Column
    private String content;

    //채팅 친 시간
    @CreationTimestamp
    @Column
    private LocalDateTime sendDate;
}
