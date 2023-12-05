package project.manager.server.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.contest.ContestPost;
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

    @Column(name = "point")
    private Integer point;

    //https://csy7792.tistory.com/240
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    // -------------------------------------------------------------------

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<ContestPost> contestPosts;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<BuildingPost> buildingPosts;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY)
    private List<Apply> applyList;

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY)
    private List<Review> reviewerList;

    @OneToMany(mappedBy = "reviewee", fetch = FetchType.LAZY)
    private List<Review> revieweeList;

    // -------------------------------------------------------------------

    @Builder
    public User(UserRequestDto userRequestDto, UserRole role) {
        this.email = userRequestDto.getEmail();
        this.name = userRequestDto.getName();
        this.nickName = userRequestDto.getNickName();
        this.introduction = userRequestDto.getIntroduction();
        this.role = role;
        this.point = 0;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.userState = UserState.MEMBER;
        this.phoneNumber = userRequestDto.getPhoneNumber();
    }

    public void updateUser(String nickName, String introduction) {
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void updatePoint(Integer point) {
        this.point = point;
    }

    public void withdrawUser() {
        this.userState = UserState.WITHDRAWAL;
    }

}
