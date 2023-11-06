package project.manager.server.domain;

import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.enums.UserRole;
import project.manager.server.enums.UserState;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_TB")
@DynamicUpdate
public class User {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    //-------------------------------------------------------------------

    @Builder
    public User(UserRequestDto userRequestDto, UserRole role) {
        this.email = userRequestDto.getEmail();
        this.name = userRequestDto.getName();
        this.nickName = userRequestDto.getNickName();
        this.sex = userRequestDto.getSex();
        this.birth = userRequestDto.getBirth();
        this.introduction = userRequestDto.getIntroduction();
        this.role = role;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.userState = UserState.MEMBER;
    }

    public void updateUser(String nickName, String introduction) {
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void deleteUser() {
        this.userState = UserState.WITHDRAWAL;
    }

}
