package project.manager.server.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.enums.UserRole;
import project.manager.server.enums.UserState;

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

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    // -------------------------------------------------------------------

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Resume resume;

    // -------------------------------------------------------------------

    @Builder
    public User(UserRequestDto userRequestDto, UserRole role) {
        this.email = userRequestDto.getEmail();
        this.name = userRequestDto.getName();
        this.nickName = userRequestDto.getNickName();
        this.introduction = userRequestDto.getIntroduction();
        this.role = role;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.userState = UserState.MEMBER;
        this.phoneNumber = userRequestDto.getPhoneNumber();
    }

    public void updateUser(String nickName, String introduction) {
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void withdrawUser() {
        this.userState = UserState.WITHDRAWAL;
    }

    public void addResume(Resume resume) {
        this.resume = resume;
        resume.setUser(this);
    }
}
