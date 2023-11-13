package project.manager.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.resume.ResumeDto;
import project.manager.server.dto.request.resume.ResumeRequestDto;
import project.manager.server.service.resume.ResumeService;


@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/{userId}")
    public ResponseDto<Long> creatResume(@PathVariable Long userId, @Valid @RequestBody ResumeRequestDto resumeRequestDto) {
        return new ResponseDto<Long>(resumeService.createResume(userId, resumeRequestDto));
    }

    @GetMapping("/{userId}")
    public ResponseDto<ResumeDto> readResume(@PathVariable Long userId) {
        return new ResponseDto<ResumeDto>(resumeService.readResume(userId));
    }
}
