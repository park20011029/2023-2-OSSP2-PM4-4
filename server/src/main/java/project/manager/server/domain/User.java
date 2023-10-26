package project.manager.server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.domain.enumtype.UserRole;
import project.manager.server.domain.enumtype.UserState;

import java.sql.Timestamp;
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

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "is_login", columnDefinition = "boolean default false", nullable = false)
    private Boolean isLogin;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Builder
    public User(String email, String name, String nickName, String introduction, UserRole role) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.introduction = introduction;
        this.role = role;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.isLogin = false;
        this.refreshToken = null;
        this.userState = UserState.MEMBER;
    }

    //-------------------------------------------------------------------

    public void updateUser(String nickName, String introduction, UserState userState) {
        this.nickName = nickName;
        this.introduction = introduction;
        this.userState = userState;
    }

    public void deleteUser() {
        this.userState = UserState.WITHDRAWAL;
    }

    public void updateRefreshToken(String refreshToken) {
        this.isLogin = true;
        this.refreshToken = refreshToken;
    }

    public void signOut() {
        this.isLogin = false;
        this.refreshToken = null;
    }
}
