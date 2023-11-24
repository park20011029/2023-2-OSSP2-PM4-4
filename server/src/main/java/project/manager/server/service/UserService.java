package project.manager.server.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.dto.reponse.UserDto;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.enums.UserRole;
import project.manager.server.enums.UserState;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new ApiException((ErrorDefine.EMAIL_EXIST));
        } else if (userRepository.existsByNickName(userRequestDto.getNickName())) {
            throw new ApiException(ErrorDefine.NICKNAME_EXIST);
        }

        User newUser = User.builder().userRequestDto(userRequestDto).role(UserRole.USER).build();

        userRepository.save(newUser);

        return newUser.getId();
    }

    public UserDto readUserProfile(Long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        if (user.getUserState() == UserState.WITHDRAWAL) {
            throw new ApiException(ErrorDefine.USER_WITHDRAWAL);
        } else if (user.getUserState() == UserState.EXPEL) {
            throw new ApiException(ErrorDefine.USER_EXPEL);
        } else if (user.getUserState() == UserState.DELETE) {
            throw new ApiException(ErrorDefine.USER_DELETE);
        }

        return UserDto.builder().user(user).build();
    }

    @Transactional
    public UserDto updateUserProfile(Long userId, UserRequestDto userRequestDto) {
        User user =
                userRepository
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

        return UserDto.builder().user(user).build();
    }

    @Transactional
    public UserDto withdrawUser(Long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        if (user.getUserState() == UserState.WITHDRAWAL) {
            throw new ApiException(ErrorDefine.USER_WITHDRAWAL);
        } else if (user.getUserState() == UserState.EXPEL) {
            throw new ApiException(ErrorDefine.USER_EXPEL);
        } else if (user.getUserState() == UserState.DELETE) {
            throw new ApiException(ErrorDefine.USER_DELETE);
        }

        user.withdrawUser();

        return UserDto.builder().user(user).build();
    }
}
