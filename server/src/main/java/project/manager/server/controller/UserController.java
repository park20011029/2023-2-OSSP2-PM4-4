package project.manager.server.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.reponse.UserDto;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.dto.request.report.UserReportRequestDto;
import project.manager.server.service.UserService;
import project.manager.server.service.report.UserReportService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserReportService userReportService;

    //회원가입시
//    @PostMapping("/signup")
//    public ResponseDto<Long> userSignUp(
//            @Valid @RequestPart UserRequestDto userRequestDto,
//            @RequestPart MultipartFile file) {
//
//        return new ResponseDto<>(userService.createUser(userRequestDto, file));
//    }

    @PostMapping("/report")
    public ResponseDto<Boolean> userProfileReport(@Valid @RequestBody UserReportRequestDto userReportRequestDto) {

        return new ResponseDto<>(userReportService.createUserReport(userReportRequestDto));
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
    public ResponseDto<Boolean> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userRequestDto) {

        return new ResponseDto<>(userService.updateUserProfile(userId, userRequestDto));
    }

    //프로필 사진 수정
    @PutMapping("/userImage/{userId}")
    public ResponseDto<Boolean> updaterProfileImage(
            @PathVariable Long userId,
            @RequestPart MultipartFile file) {

        return new ResponseDto<>(userService.updateUserImage(userId, file));
    }

    @PutMapping("/signOut")
    ResponseDto<Boolean> signOut(@RequestParam("userId") Long userId) {

        return new ResponseDto<>(userService.signOut(userId));
    }

    //회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseDto<Boolean> withdrawUser(@PathVariable Long userId) {

        return new ResponseDto<>(userService.withdrawUser(userId));
    }

}
