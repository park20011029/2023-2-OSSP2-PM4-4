package project.manager.server.service;

import static project.manager.server.enums.Constant.REVIEW_POINT;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.Review;
import project.manager.server.dto.reponse.ReviewDto;
import project.manager.server.dto.reponse.post.PageInfo;
import project.manager.server.dto.request.ReviewRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ReviewRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /** 받은 리뷰 목록 */
    public Map<String, Object> readMyReviewList(Long userId, Integer page, Integer size) {
        Page<Review> reviews = reviewRepository.findMyReviewsByReviweeId(userId, PageRequest.of(page, size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(reviews.getNumber() + 1)
                .totalPages(reviews.getTotalPages())
                .pageSize(reviews.getSize())
                .currentItems(reviews.getNumberOfElements())
                .totalItems(reviews.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("myReviews", reviews.stream()
                .map(review -> ReviewDto.builder()
                        .createAt(review.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .reviewerId(review.getReviewer().getId())
                        .reviewer(review.getReviewer().getNickName())
                        .revieweeId(review.getReviewee().getId())
                        .reviewee(review.getReviewee().getNickName())
                        .projectId(review.getBuildingPost().getId())
                        .projectName(review.getBuildingPost().getTitle())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .score(review.getScore())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    /** 작성해야 할 리뷰 목록 */
    public Map<String, Object> readPendingReviewList(Long userId, Integer page, Integer size) {
        Page<Review> reviews = reviewRepository.findBeforeReviewsByReviewerId(userId,PageRequest.of(page, size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(reviews.getNumber() + 1)
                .totalPages(reviews.getTotalPages())
                .pageSize(reviews.getSize())
                .currentItems(reviews.getNumberOfElements())
                .totalItems(reviews.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("pendingReviews", reviews.stream()
                .map(review -> ReviewDto.builder()
                        .createAt(review.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .reviewerId(review.getReviewer().getId())
                        .reviewer(review.getReviewer().getNickName())
                        .revieweeId(review.getReviewee().getId())
                        .reviewee(review.getReviewee().getNickName())
                        .projectId(review.getBuildingPost().getId())
                        .projectName(review.getBuildingPost().getTitle())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .score(review.getScore())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    /** 작성한 리뷰 목록 */
    public Map<String, Object> readWrittenReviewList(Long userId, Integer page, Integer size) {
        Page<Review> reviews = reviewRepository.findReviewsByReviewerId(userId,PageRequest.of(page, size, Sort.by("createdDate").descending()));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(reviews.getNumber() + 1)
                .totalPages(reviews.getTotalPages())
                .pageSize(reviews.getSize())
                .currentItems(reviews.getNumberOfElements())
                .totalItems(reviews.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("writtenReviews", reviews.stream()
                .map(review -> ReviewDto.builder()
                        .createAt(review.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .reviewerId(review.getReviewer().getId())
                        .reviewer(review.getReviewer().getNickName())
                        .revieweeId(review.getReviewee().getId())
                        .reviewee(review.getReviewee().getNickName())
                        .projectId(review.getBuildingPost().getId())
                        .projectName(review.getBuildingPost().getTitle())
                        .content(review.getContent())
                        .reviewId(review.getId())
                        .score(review.getScore())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Boolean writeReview(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findByIdAndContentIsNull(reviewId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        review.updateReview(reviewRequestDto.getContent(), reviewRequestDto.getScore());
        review.getReviewer().updatePoint(review.getReviewer().getPoint() + REVIEW_POINT);

        return true;
    }

}
