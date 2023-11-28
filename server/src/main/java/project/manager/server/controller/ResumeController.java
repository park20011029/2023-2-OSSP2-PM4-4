package project.manager.server.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.resume.ResumeDto;
import project.manager.server.dto.request.resume.AwardRequestDto;
import project.manager.server.dto.request.resume.ProjectRequestDto;
import project.manager.server.dto.request.resume.ResumeRequestDto;
import project.manager.server.dto.request.resume.TechStackRequestDto;
import project.manager.server.service.resume.*;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final ProjectService projectService;
    private final TechStackService techStackService;
    private final AwardService awardService;

    //이력서 생성
    @PostMapping("/{userId}")
    public ResponseDto<Long> creatResume(
            @PathVariable Long userId,
            @Valid @RequestBody ResumeRequestDto resumeRequestDto) {

        return new ResponseDto<>(resumeService.createResume(userId, resumeRequestDto));
    }

    //이력서 읽어오기
    @GetMapping("/{resumeId}")
    public ResponseDto<ResumeDto> readResume(@PathVariable Long resumeId) {

        return new ResponseDto<>(resumeService.readResume(resumeId));
    }

    //이력서 수정에서 프로젝트 경험 목록 추가할 때마다 사용
    @PostMapping("/project/{resumeId}")
    public ResponseDto<Long> addProject(
            @PathVariable Long resumeId,
            @Valid @RequestBody ProjectRequestDto projectRequestDto) {

        return new ResponseDto<>(resumeService.addProject(resumeId, projectRequestDto));
    }

    //이력서 수정에서 기술스택 목록 추가할 때마다 사용
    @PostMapping("/techStack/{resumeId}")
    public ResponseDto<Long> addTechStack(
            @PathVariable Long resumeId,
            @Valid @RequestBody TechStackRequestDto techStackRequestDto) {

        return new ResponseDto<>(resumeService.addTechStack(resumeId, techStackRequestDto));
    }

    //이력서 수정에서 수상내역 목록 추가할 때마다 사용
    @PostMapping("/award/{resumeId}")
    public ResponseDto<Long> addAward(
            @PathVariable Long resumeId,
            @Valid @RequestBody AwardRequestDto awardRequestDto) {

        return new ResponseDto<>(resumeService.addAward(resumeId, awardRequestDto));
    }

    //이력서 수정 후
    @PutMapping("/{resumeId}")
    public ResponseDto<Boolean> updateResume(
            @PathVariable Long resumeId,
            @Valid @RequestBody ResumeRequestDto resumeRequestDto) {

        return new ResponseDto<>(resumeService.updateResume(resumeId, resumeRequestDto));
    }

    //이력서 수정에서 프로젝트 목록 삭제할 때마다 사용
    @DeleteMapping("/project/{projectId}")
    public ResponseDto<Boolean> deleteProject(@PathVariable Long projectId) {

        return new ResponseDto<>(projectService.deleteProject(projectId));
    }

    //이력서 수정에서 수상내역 목록 삭제할 때마다 사용
    @DeleteMapping("/award/{awardId}")
    public ResponseDto<Boolean> deleteAward(@PathVariable Long awardId) {

        return new ResponseDto<>(awardService.deleteAward(awardId));
    }

    //이력서 수정에서 기술스택 목록 삭제할 때마다 사용
    @DeleteMapping("/techStack/{techStackId}")
    public ResponseDto<Boolean> deleteTechStack(@PathVariable Long techStackId) {

        return new ResponseDto<>(techStackService.deleteTechStack(techStackId));
    }
}
