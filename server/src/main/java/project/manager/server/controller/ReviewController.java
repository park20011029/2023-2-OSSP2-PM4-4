package project.manager.server.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.ReviewRequestDto;
import project.manager.server.service.ReviewService;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 작성하기
    @PutMapping("/{reviewId}")
    public ResponseDto<Boolean> writeReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDto reviewRequestDto) {

        return new ResponseDto<>(reviewService.writeReview(reviewId, reviewRequestDto));
    }
}
