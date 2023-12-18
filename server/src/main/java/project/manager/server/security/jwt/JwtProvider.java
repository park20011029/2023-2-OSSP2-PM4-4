package project.manager.server.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import project.manager.server.enums.UserRole;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {
    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;
    private static final Long accessExpiredMs = 60 * 60 * 2 * 1000L;
    private static final Long refreshExpiredMs = 60 * 60 * 24 * 60 * 1000L;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Long id, UserRole userRole, boolean isAccess) {
        Claims claims = Jwts.claims();

        claims.put("id", id);
        if(isAccess) {
            claims.put("userRole", userRole);
        }

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (isAccess ? accessExpiredMs : refreshExpiredMs)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public JwtToken createTotalToken(Long id, UserRole userRole) {

        //Access token 생성
        String accessToken = createToken(id, userRole, true);

        //Refresh token 생성
        String refreshToken = createToken(id, userRole, false);

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String validRefreshToken(HttpServletRequest request) throws JwtException {

        String refreshToken = refineToken(request);

        Claims claims = validateToken(refreshToken);

        UserRepository.UserLoginForm user = userRepository.findByIdAndRefreshToken(Long.valueOf(claims.get("id").toString()), refreshToken)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        return createToken(user.getId(), user.getUserRole(), true);
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id").toString();
    }

    public Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String refineToken(HttpServletRequest request) throws JwtException {
        String beforeToken = request.getHeader("Authorization");

        String afterToken = null;
        if (StringUtils.hasText(beforeToken) && beforeToken.startsWith("Bearer ")) {
            afterToken =  beforeToken.substring(7);
        } else {
            throw new IllegalArgumentException("Not Valid Or Not Exist Token");
        }

        return afterToken;
    }
}
