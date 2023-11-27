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

        return new ResponseDto<Long>(userService.createUser(userRequestDto));
    }

    //프로필 읽어오기
    @GetMapping("/{userId}")
    public ResponseDto<UserDto> getUser(@PathVariable Long userId) {

        return new ResponseDto<UserDto>(userService.readUserProfile(userId));
    }

    //포로필 수정
    @PutMapping("/{userId}")
    public ResponseDto<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {

        return new ResponseDto<UserDto>(userService.updateUserProfile(userId, userRequestDto));
    }

    //회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseDto<UserDto> withdrawUser(@PathVariable Long userId) {

        return new ResponseDto<UserDto>(userService.withdrawUser(userId));
    }
}
