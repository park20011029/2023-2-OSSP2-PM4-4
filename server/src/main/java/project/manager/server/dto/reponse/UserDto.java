package project.manager.server.dto.reponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.User;
import project.manager.server.json.NullToNullStringSerializer;

@Getter
@NoArgsConstructor
public class UserDto {

    @JsonSerialize(using = NullToNullStringSerializer.class)
    private Long resumeId;

    private Long userId;
    private String name;
    private String email;
    private String nickName;
    private String introduction;
    private String phoneNumber;

    @Builder
    public UserDto(User user) {
        this.resumeId = (user.getResume() != null) ? user.getResume().getId() : null;
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.phoneNumber = user.getPhoneNumber();
    }
}
