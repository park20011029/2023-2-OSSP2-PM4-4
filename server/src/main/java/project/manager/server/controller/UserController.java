package project.manager.server.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.UserDto;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입시
    @PostMapping("/signup")
    public ResponseDto<Long> userSignUp(@Valid @RequestBody UserRequestDto userRequestDto) {

        return new ResponseDto<>(userService.createUser(userRequestDto));
    }

    //프로필 읽어오기
    @GetMapping("/{userId}")
    public ResponseDto<UserDto> getUser(@PathVariable Long userId) {

        return new ResponseDto<>(userService.readUserProfile(userId));
    }

    //닉네임 중복 확인
    @GetMapping("/nickname/{nickName}")
    public ResponseDto<Boolean> existNickName(@PathVariable String nickName){

        return new ResponseDto<>(userService.existNickName(nickName));
    }

    //포로필 수정
    @PutMapping("/{userId}")
    public ResponseDto<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userRequestDto) {

        return new ResponseDto<>(userService.updateUserProfile(userId, userRequestDto));
    }

    //회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseDto<Boolean> withdrawUser(@PathVariable Long userId) {

        return new ResponseDto<>(userService.withdrawUser(userId));
    }

}
