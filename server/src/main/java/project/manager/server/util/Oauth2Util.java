package project.manager.server.util;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2Util {
    //google
    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String GOOGLE_TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String GOOGLE_USERINFO_URL;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

    //Spring 에서 RestTemplate 보다 WebClient 를 권장 deprecate 됨
    private final RestTemplate restTemplate = new RestTemplate();

    public String getGoogleAccessToken(String authCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", GOOGLE_REDIRECT_URL);
        params.add("code", authCode);

        HttpEntity<MultiValueMap<String,String>> tokenRequest = new HttpEntity<>(params,httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_TOKEN_URL,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );

        return JsonParser.parseString(Objects.requireNonNull(response.getBody()))
                .getAsJsonObject().get("accessToken").getAsString();
    }

    public Oauth2UserInfo getGoogleUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer $accessToken" + accessToken);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String,String >> profileRequest= new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_USERINFO_URL,
                HttpMethod.GET,
                profileRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
        return Oauth2UserInfo.builder()
                .socialId(element.getAsJsonObject().getAsJsonObject("response").get("id").getAsString())
                .socialName(element.getAsJsonObject().getAsJsonObject("response").get("name").getAsString())
                .email(element.getAsJsonObject().getAsJsonObject("response").get("email").getAsString())
                .build();
    }

}
