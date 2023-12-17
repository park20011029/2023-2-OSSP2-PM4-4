package project.manager.server.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtToken {
    private String access_token;
    private String refresh_token;
}
