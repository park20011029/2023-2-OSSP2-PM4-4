package project.manager.server.controller;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.request.report.ResumeReportRequestDto;
import project.manager.server.dto.request.resume.create.AwardRequestDto;
import project.manager.server.dto.request.resume.create.ProjectRequestDto;
import project.manager.server.dto.request.resume.create.ResumeRequestDto;
import project.manager.server.dto.request.resume.create.TechStackRequestDto;
import project.manager.server.dto.request.resume.update.ResumeUpdateDto;
import project.manager.server.service.report.ResumeReportService;
import project.manager.server.service.resume.*;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final ProjectService projectService;
    private final TechStackService techStackService;
    private final AwardService awardService;
    private final ResumeReportService resumeReportService;

    //이력서 생성
    @PostMapping("/{userId}")
    public ResponseDto<Long> creatResume(
            @PathVariable Long userId,
            @Valid @RequestBody ResumeRequestDto resumeRequestDto) {

        return new ResponseDto<>(resumeService.createResume(userId, resumeRequestDto));
    }

    //이력서 읽어오기
    @GetMapping("/{resumeId}")
    public ResponseDto<Map<String, Object>> readResume(@PathVariable Long resumeId) {

        return new ResponseDto<>(resumeService.readResume(resumeId));
    }

    //이력서 수정에서 프로젝트 경험 목록 추가할 때마다 사용
    @PostMapping("/project/{resumeId}")
    public ResponseDto<Long> addProject(
            @PathVariable Long resumeId,
            @Valid @RequestBody ProjectRequestDto projectRequestDto) {

        return new ResponseDto<>(projectService.addProject(resumeId, projectRequestDto));
    }

    //이력서 수정에서 기술스택 목록 추가할 때마다 사용
    @PostMapping("/techStack/{resumeId}")
    public ResponseDto<Long> addTechStack(
            @PathVariable Long resumeId,
            @Valid @RequestBody TechStackRequestDto techStackRequestDto) {

        return new ResponseDto<>(techStackService.addTechStack(resumeId, techStackRequestDto));
    }

    //이력서 수정에서 수상내역 목록 추가할 때마다 사용
    @PostMapping("/award/{resumeId}")
    public ResponseDto<Long> addAward(
            @PathVariable Long resumeId,
            @Valid @RequestBody AwardRequestDto awardRequestDto) {

        return new ResponseDto<>(awardService.addAward(resumeId, awardRequestDto));
    }

    @PostMapping("/report")
    public ResponseDto<Boolean> resumeReport(@Valid @RequestBody ResumeReportRequestDto resumeReportRequestDto) {

        return new ResponseDto<>(resumeReportService.creatResumeReport(resumeReportRequestDto));
    }

    //이력서 수정 후
    @PutMapping("/{resumeId}")
    public ResponseDto<Boolean> updateResume(
            @PathVariable Long resumeId,
            @Valid @RequestBody ResumeUpdateDto resumeUpdateDto) {

        return new ResponseDto<>(resumeService.updateResume(resumeId, resumeUpdateDto));
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
