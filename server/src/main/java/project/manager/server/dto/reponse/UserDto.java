package project.manager.server.dto.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.User;
import project.manager.server.json.NullToNullStringSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Getter
@NoArgsConstructor
public class UserDto {

    @JsonSerialize(using = NullToNullStringSerializer.class)
    private Long resumeId;

    private Integer point;
    private String url;
    private Long userId;
    private String name;
    private String email;
    private String nickName;
    private String introduction;
    private String phoneNumber;
    private String userRole;

    @Builder
    public UserDto(User user, Long resumeId) {
        this.url = user.getUserImage().getUrl();
        this.resumeId = resumeId;
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.phoneNumber = user.getPhoneNumber();
        this.point = user.getPoint();
        this.userRole = user.getUserState().name();
    }
}
