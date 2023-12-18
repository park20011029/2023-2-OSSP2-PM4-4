package project.manager.server.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.reponse.SigninDto;
import project.manager.server.dto.request.UserRequestDto;
import project.manager.server.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    ResponseDto<Long> signUp(
            @RequestPart MultipartFile file,
            @Valid @RequestPart UserRequestDto userRequestDto) {

        return new ResponseDto<>(authService.createUser(userRequestDto, file));
    }

    @GetMapping("/socialInfo")
    ResponseDto<String> getSocialInfo(@RequestParam("authCode") String authCode) {

        return new ResponseDto<>(authService.socialInfo(authCode));
    }

    @PutMapping("/signIn")
    ResponseDto<Long> signIn(@RequestParam("authCode") String authCode) {

        return new ResponseDto<>(authService.socialSignIn(authCode));
    }
}
