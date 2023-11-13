package project.manager.server.dto.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.User;

@Getter
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private Long resumeId;
    private String name;
    private String email;
    private String nickName;
    private String introduction;
    private String phoneNumber;

    @Builder
    public UserDto(User user) {
        this.resumeId = user.getResume().getId();
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.phoneNumber = user.getPhoneNumber();
    }
}
