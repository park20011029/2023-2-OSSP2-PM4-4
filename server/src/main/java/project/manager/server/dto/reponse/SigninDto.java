package project.manager.server.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SigninDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
