package project.manager.server.controller.manage;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.reponse.post.contest.ContestPostDto;
import project.manager.server.service.post.contest.ContestPostService;
import project.manager.server.service.report.ContestReportService;

@RestController
@RequestMapping("/contestPostReport")
@RequiredArgsConstructor
public class ContestReportController {

    private final ContestReportService contestReportService;
    private final ContestPostService contestPostService;

    /** 팀 빌딩 게시글 신고 리스트 */
    @GetMapping("")
    ResponseDto<Map<String, Object>> readReportList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(contestReportService.readReportList(page, size));
    }

    /** 신고된 팀 빌딩 게시글 */
    @GetMapping("/contestPost/{contentId}")
    public ResponseDto<ContestPostDto> readReportedContest(@PathVariable Long contentId) {

        return new ResponseDto<>(contestPostService.readContestPost(contentId));
    }

    /** 신고된 회원 처벌 */
    @PutMapping("/execute/{reportId}/{penalty}")
    public ResponseDto<Boolean> executeUser(@PathVariable Long reportId, @PathVariable Long penalty) {

        return new ResponseDto<>(contestReportService.executeUser(reportId, penalty));
    }

    /** 유저 추방 */
    @PutMapping("/expel/{reportId}")
    public ResponseDto<Boolean> expelUser(@PathVariable Long reportId) {

        return new ResponseDto<>(contestReportService.expelUser(reportId));
    }

    /** 신고 내역 삭제 */
    @PutMapping("/delete/{reportId}")
    public ResponseDto<Boolean> deleteReport(@PathVariable Long reportId) {

        return new ResponseDto<>(contestReportService.deleteReport(reportId));
    }
}
