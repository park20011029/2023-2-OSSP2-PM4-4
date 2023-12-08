package project.manager.server.controller.manage;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.reponse.ReviewDto;
import project.manager.server.service.report.ReviewReportService;

@RestController
@RequestMapping("/reviewReport")
@RequiredArgsConstructor
public class ReviewReportController {

    private final ReviewReportService reviewReportService;

    /** 리뷰 신고 리스트 */
    @GetMapping("")
    ResponseDto<Map<String, Object>> readReportList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(reviewReportService.readReportList(page, size));
    }

    /** 신고된 리뷰 */
    @GetMapping("/review/{contentId}")
    public ResponseDto<ReviewDto> readReportedReview(@PathVariable Long contentId) {

        return new ResponseDto<>(reviewReportService.readReviewReport(contentId));
    }

    /** 신고된 회원 처벌 */
    @PutMapping("/execute/{reportId}/{penalty}")
    public ResponseDto<Boolean> executeReview(@PathVariable Long reportId, @PathVariable Long penalty) {

        return new ResponseDto<>(reviewReportService.executeUser(reportId, penalty));
    }

    /** 유저 추방 */
    @PutMapping("/expel/{reportId}")
    public ResponseDto<Boolean> expelReview(@PathVariable Long reportId) {

        return new ResponseDto<>(reviewReportService.expelUser(reportId));
    }

    /** 신고 내역 삭제 */
    @PutMapping("/delete/{reportId}")
    public ResponseDto<Boolean> deleteReport(@PathVariable Long reportId) {

        return new ResponseDto<>(reviewReportService.deleteReport(reportId));
    }
}
