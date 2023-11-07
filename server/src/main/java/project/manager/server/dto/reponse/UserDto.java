package project.manager.server.dto.reponse;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.manager.server.domain.User;

@Getter
@AllArgsConstructor
public class UserDto {

    private final String name;
    private final String email;
    private final String nickName;
    private final Boolean sex;
    private final LocalDate birth;
    private final String introduction;

    @Builder
    public UserDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.sex = user.getSex();
        this.birth = user.getBirth();
        this.introduction = user.getIntroduction();
    }
}
