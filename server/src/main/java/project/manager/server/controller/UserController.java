package project.manager.server.controller;

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
    public ResponseDto<UserDto> userSignUp(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseDto<UserDto>(userService.createUser(userRequestDto));
    }

    @GetMapping("/{nickName}")
    public ResponseDto<UserDto> getUser(@PathVariable String nickName) {
        return new ResponseDto<UserDto>(userService.readUserProfile(nickName));
    }

    @PutMapping("/{nickName}")
    public ResponseDto<UserDto> updateUser(
            @PathVariable String nickName, @RequestBody UserRequestDto userRequestDto) {
        return new ResponseDto<UserDto>(userService.updateUserProfile(nickName, userRequestDto));
    }

    @DeleteMapping("/{nickName}")
    public ResponseDto<UserDto> withdrawUser(@PathVariable String nickName) {
        return new ResponseDto<UserDto>(userService.deleteUser(nickName));
    }
}