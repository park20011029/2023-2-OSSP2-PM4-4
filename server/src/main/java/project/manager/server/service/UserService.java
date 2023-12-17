package project.manager.server.service;


import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.domain.Image;
import project.manager.server.domain.User;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.reponse.UserDto;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.enums.UserRole;
import project.manager.server.enums.UserState;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ImageRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.resume.ResumeRepository;
import project.manager.server.util.S3UploadUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;

    public Long createUser(UserRequestDto userRequestDto, MultipartFile file) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new ApiException((ErrorDefine.EMAIL_EXIST));
        } else if (userRepository.existsByNickName(userRequestDto.getNickName())) {
            throw new ApiException(ErrorDefine.NICKNAME_EXIST);
        }

        String url = s3UploadUtil.upload(file, "pm4/");
        User newUser = User.builder()
                .userRequestDto(userRequestDto)
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

    public UserDto readUserProfile(Long userId) {
        User user = userRepository
                        .findByUserWithImage(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        if (user.getUserState() == UserState.WITHDRAWAL) {
            throw new ApiException(ErrorDefine.USER_WITHDRAWAL);
        } else if (user.getUserState() == UserState.EXPEL) {
            throw new ApiException(ErrorDefine.USER_EXPEL);
        } else if (user.getUserState() == UserState.DELETE) {
            throw new ApiException(ErrorDefine.USER_DELETE);
        }

        return UserDto.builder()
                .user(user)
                .resumeId(resumeRepository.findByUserId(userId)
                        .map(Resume::getId)
                        .orElse(null))
                .build();
    }

    public Boolean updateUserProfile(Long userId, UserRequestDto userRequestDto) {
        User user = userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        if (userRepository.existsByIdNotAndNickName(user.getId(), userRequestDto.getNickName())) {
            throw new ApiException(ErrorDefine.NICKNAME_EXIST);
        } else if (user.getUserState() == UserState.WITHDRAWAL) {
            throw new ApiException(ErrorDefine.USER_WITHDRAWAL);
        } else if (user.getUserState() == UserState.EXPEL) {
            throw new ApiException(ErrorDefine.USER_EXPEL);
        } else if (user.getUserState() == UserState.DELETE) {
            throw new ApiException(ErrorDefine.USER_DELETE);
        }

        user.updateUser(userRequestDto.getNickName(), userRequestDto.getIntroduction());

        return true;
    }

    public Boolean updateUserImage(Long userId, MultipartFile file) {
        User user = userRepository
                .findByUserWithImage(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        String url = s3UploadUtil.upload(file, "pm4/");
        Image newImage = Image.builder()
                .url(url)
                .uploader(user)
                .build();
        imageRepository.save(newImage);

        user.updateImage(newImage);

        return true;
    }

    public Boolean withdrawUser(Long userId) {
        User user = userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        if (user.getUserState() == UserState.WITHDRAWAL) {
            throw new ApiException(ErrorDefine.USER_WITHDRAWAL);
        } else if (user.getUserState() == UserState.EXPEL) {
            throw new ApiException(ErrorDefine.USER_EXPEL);
        } else if (user.getUserState() == UserState.DELETE) {
            throw new ApiException(ErrorDefine.USER_DELETE);
        }

        user.updateUserState(UserState.WITHDRAWAL, LocalDate.now());

        return true;
    }

    public Boolean existNickName(String nickName){
        return userRepository.existsByNickName(nickName);
    }

    public Boolean signOut(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        user.signOutUser();

        return true;
    }
}
