package project.manager.server.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.domain.Image;
import project.manager.server.domain.User;
import project.manager.server.dto.reponse.SigninDto;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.enums.UserRole;
import project.manager.server.enums.UserState;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ImageRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.security.jwt.JwtProvider;
import project.manager.server.security.jwt.JwtToken;
import project.manager.server.util.Oauth2UserInfo;
import project.manager.server.util.Oauth2Util;
import project.manager.server.util.S3UploadUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final Oauth2Util oauth2Util;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final S3UploadUtil s3UploadUtil;

    public Oauth2UserInfo returnSocialInfo(String authCode) {
        String accessTokenGoogle = oauth2Util.getGoogleAccessToken(authCode);
        return oauth2Util.getGoogleUserInfo(accessTokenGoogle);
    }

    public String socialInfo(String authCode) {
        String accessTokenGoogle = oauth2Util.getGoogleAccessToken(authCode);
        Oauth2UserInfo oauth2InfoGoogle = oauth2Util.getGoogleUserInfo(accessTokenGoogle);

        return oauth2InfoGoogle.getEmail();
    }
    public Long socialSignIn(String authCode) {

        String accessTokenGoogle = oauth2Util.getGoogleAccessToken(authCode);
        Oauth2UserInfo oauth2InfoGoogle = oauth2Util.getGoogleUserInfo(accessTokenGoogle);
        String socialId = oauth2InfoGoogle.getSocialId();

        User signinUser = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        if (signinUser.getUserState().equals(UserState.EXPEL)) {
            throw new ApiException(ErrorDefine.USER_EXPEL);
        } if (signinUser.getUserState().equals(UserState.WITHDRAWAL)) {
            throw new ApiException(ErrorDefine.USER_WITHDRAWAL);
        } if (signinUser.getUserState().equals(UserState.PENALTY)) {
            throw new ApiException(ErrorDefine.USER_PENALTY);
        } if (signinUser.getUserState().equals(UserState.DELETE)) {
            throw new ApiException(ErrorDefine.USER_DELETE);
        }

        //JwtToken jwtToken = jwtProvider.createTotalToken(signinUser.getId(), signinUser.getRole());
        //signinUser.updateRefreshToken(jwtToken.getRefreshToken());

//        return SigninDto.builder()
//                .userId(signinUser.getId())
//                .accessToken(jwtToken.getAccessToken())
//                .refreshToken(jwtToken.getRefreshToken())
//                .build();
        return signinUser.getId();
    }

    public Long createUser(UserRequestDto userRequestDto, MultipartFile file) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new ApiException((ErrorDefine.EMAIL_EXIST));
        } else if (userRepository.existsByNickName(userRequestDto.getNickName())) {
            throw new ApiException(ErrorDefine.NICKNAME_EXIST);
        }
        if (userRequestDto.getAuthCode() == null) {
            throw new ApiException(ErrorDefine.ENTITY_NOT_FOUND);
        }

        String accessTokenGoogle = oauth2Util.getGoogleAccessToken(userRequestDto.getAuthCode());
        Oauth2UserInfo oauth2InfoGoogle = oauth2Util.getGoogleUserInfo(accessTokenGoogle);
        String socialId = oauth2InfoGoogle.getSocialId();

        User signUpUser = userRepository.findBySocialId(socialId)
                .orElse(null);

        if (signUpUser != null) {
            throw new ApiException(ErrorDefine.USER_EXIST);
        }

        //signinUser.updateRefreshToken(jwtToken.getRefreshToken());

        String url = s3UploadUtil.upload(file, "pm4/");
        User newUser = User.builder()
                .userRequestDto(userRequestDto)
                .socialId(socialId)
                .role(UserRole.USER).build();
        userRepository.save(newUser);

        Image newImage = Image.builder()
                .url(url)
                .uploader(newUser)
                .build();
        imageRepository.save(newImage);
        newUser.updateImage(newImage);

        return newUser.getId();
    }

}
