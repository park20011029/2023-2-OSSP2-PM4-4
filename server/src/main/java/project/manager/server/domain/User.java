package project.manager.server.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.chat.Chat;
import project.manager.server.domain.chat.ChatRoom;
import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.enums.Constant;
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

    @Column(name = "point")
    private Integer point;

    //https://csy7792.tistory.com/240
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "execute_date")
    private LocalDate executeDate;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isLogin;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "refresh_Token")
    private String refreshToken;

    // -------------------------------------------------------------------

//    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
//    private List<ContestPost> contestPosts;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<BuildingPost> buildingPosts;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY)
    private List<Apply> applyList;

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY)
    private List<Review> reviewerList;

    @OneToMany(mappedBy = "reviewee", fetch = FetchType.LAZY)
    private List<Review> revieweeList;

    @OneToMany(mappedBy = "receiverId", fetch = FetchType.LAZY)
    private List<ChatRoom> userList;

    @OneToMany(mappedBy = "postWriterId", fetch = FetchType.LAZY)
    private List<ChatRoom> postWriterList;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Chat> senderList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userimage_id")
    private Image userImage;

    // -------------------------------------------------------------------

    @Builder
    public User(UserRequestDto userRequestDto, UserRole role, String socialId) {
        this.email = userRequestDto.getEmail();
        this.name = userRequestDto.getName();
        this.nickName = userRequestDto.getNickName();
        this.introduction = userRequestDto.getIntroduction();
        this.role = role;
        this.isLogin = false;
        this.refreshToken = null;
        this.point = 0;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.userState = UserState.MEMBER;
        this.phoneNumber = userRequestDto.getPhoneNumber();
        this.executeDate = Constant.MYSQL_MIN_DATE;
        this.socialId = socialId;
    }

    public void updateImage(Image image) {
        this.userImage = image;
    }
    public void updateUser(String nickName, String introduction) {
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void updateRefreshToken(String refreshToken) {
        this.isLogin = true;
        this.refreshToken = refreshToken;
    }

    public void signOutUser() {
        this.isLogin = false;
        this.refreshToken = null;
    }


    public void updateUserState(UserState userState, LocalDate executeDate) {
        this.userState = userState;
        this.executeDate = executeDate;
    }

    public void updatePoint(Integer point) {
        this.point = point;
    }


}
