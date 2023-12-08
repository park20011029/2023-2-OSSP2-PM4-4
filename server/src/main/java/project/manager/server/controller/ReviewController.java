package project.manager.server.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.request.ReviewRequestDto;
import project.manager.server.dto.request.report.ReviewReportRequestDto;
import project.manager.server.service.ReviewService;
import project.manager.server.service.report.ReviewReportService;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewReportService reviewReportService;

    //리뷰 작성하기
    @PutMapping("/{reviewId}")
    public ResponseDto<Boolean> writeReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {

        return new ResponseDto<>(reviewService.writeReview(reviewId, reviewRequestDto));
    }

    @PostMapping("/report")
    public ResponseDto<Boolean> reviewReport(@Valid @RequestBody ReviewReportRequestDto reviewReportRequestDto) {

        return new ResponseDto<>(reviewReportService.creatReviewReport(reviewReportRequestDto));
    }
}
