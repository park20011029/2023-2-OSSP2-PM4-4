package project.manager.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.manager.server.domain.User;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.ResumeDto;
import project.manager.server.dto.request.ResumeRequestDto;

    @RestController
    @RequestMapping("/resume")
    @RequiredArgsConstructor
    public class ResumeController {
        private  final project.manager.server.service.ResumeService resumeService;

        @PostMapping("/signup")
        public ResponseDto<ResumeDto> resumeSignUp(@RequestBody ResumeRequestDto resumeRequestDto){
            return new ResponseDto<ResumeDto>(resumeService.createResume(resumeRequestDto));
        }

        @PutMapping("/{userId}")
        public ResponseDto<ResumeDto> updateResume(
                @PathVariable User user, @RequestBody ResumeRequestDto resumeRequestDto
        ){
            return new ResponseDto<ResumeDto>(resumeService.updateResume(user, resumeRequestDto));
        }

    }

