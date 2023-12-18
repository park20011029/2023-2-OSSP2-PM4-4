package project.manager.server.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Oauth2UserInfo {

    private String socialId;
    private String socialName;
    private String email;
}
