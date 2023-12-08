package project.manager.server.controller.manage;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.service.report.ResumeReportService;
import project.manager.server.service.resume.ResumeService;

@RestController
@RequestMapping("/resumeReport")
@RequiredArgsConstructor
public class ResumeReportController {

    private final ResumeReportService resumeReportService;
    private final ResumeService resumeService;

    /** 이력서 신고 리스트 */
    @GetMapping("")
    ResponseDto<Map<String, Object>> readReportList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(resumeReportService.readReportList(page, size));
    }

    /** 신고된 이력서 */
    @GetMapping("/resume/{contentId}")
    public ResponseDto<Map<String, Object>> readReportedResume(@PathVariable Long contentId) {

        return new ResponseDto<>(resumeService.readResume(contentId));
    }

    /** 신고된 회원 처벌 */
    @PutMapping("/execute/{reportId}/{penalty}")
    public ResponseDto<Boolean> executeUser(@PathVariable Long reportId, @PathVariable Long penalty) {

        return new ResponseDto<>(resumeReportService.executeUser(reportId, penalty));
    }

    /** 유저 추방 */
    @PutMapping("/expel/{reportId}")
    public ResponseDto<Boolean> expelUser(@PathVariable Long reportId) {

        return new ResponseDto<>(resumeReportService.expelUser(reportId));
    }

    /** 신고 내역 삭제 */
    @PutMapping("/delete/{reportId}")
    public ResponseDto<Boolean> deleteReport(@PathVariable Long reportId) {

        return new ResponseDto<>(resumeReportService.deleteReport(reportId));
    }
}
