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

    @PostMapping("/signup")
    public ResponseDto<Long> userSignUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        return new ResponseDto<Long>(userService.createUser(userRequestDto));
    }

    @GetMapping("/{userId}")
    public ResponseDto<UserDto> getUser(@PathVariable Long userId) {
        return new ResponseDto<UserDto>(userService.readUserProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseDto<UserDto> updateUser(
            @PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        return new ResponseDto<UserDto>(userService.updateUserProfile(userId, userRequestDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseDto<UserDto> withdrawUser(@PathVariable Long userId) {
        return new ResponseDto<UserDto>(userService.withdrawUser(userId));
    }
}
