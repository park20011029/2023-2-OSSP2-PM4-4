package project.manager.server.service.report;

import java.time.LocalDate;
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
import project.manager.server.domain.User;
import project.manager.server.domain.report.ReviewReport;
import project.manager.server.dto.reponse.ReviewDto;
import project.manager.server.dto.reponse.post.PageInfo;
import project.manager.server.dto.reponse.report.ReportTitle;
import project.manager.server.dto.request.report.ReviewReportRequestDto;
import project.manager.server.enums.UserState;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ReviewRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.report.ReviewReportRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewReportService {

    private final ReviewReportRepository reviewReportRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    //신고 시점 데이터 받아오기
    /** */
    public Boolean creatBuildingReport(ReviewReportRequestDto reviewReportRequestDto) {
        User reporter = userRepository.findById(reviewReportRequestDto.getReporterId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        User defendant = userRepository.findById(reviewReportRequestDto.getDefendantId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewReportRequestDto.getReviewId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        ReviewReport newReviewReport = ReviewReport.builder()
                .reporter(reporter)
                .defendant(defendant)
                .reportReason(reviewReportRequestDto.getReportReason())
                .description(reviewReportRequestDto.getDescription())
                .reportedReview(review)
                .build();

        reviewReportRepository.save(newReviewReport);

        return true;
    }

    public Map<String, Object> readReportList(Integer page, Integer size) {

        Page<ReviewReport> reports = reviewReportRepository.findAll(PageRequest.of(page, size, Sort.by("createAt").descending()));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(reports.getNumber() + 1)
                .totalPages(reports.getTotalPages())
                .pageSize(reports.getSize())
                .currentItems(reports.getNumberOfElements())
                .totalItems(reports.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("reportTitles", reports.stream()
                .map(report -> ReportTitle.builder()
                        .reportId(report.getId())
                        .reporterId(report.getReporter().getId())
                        .contentId(report.getReportedReview().getId())
                        .reporterNickName(report.getReporter().getNickName())
                        .defendantId(report.getDefendant().getId())
                        .defendantNickName(report.getDefendant().getNickName())
                        .reportReason(report.getReportReason().getMessage())
                        .createAt(report.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public ReviewDto readReviewReport(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        return ReviewDto.builder()
                .reviewerId(review.getReviewer().getId())
                .reviewer(review.getReviewer().getName())
                .revieweeId(review.getReviewee().getId())
                .reviewee(review.getReviewee().getName())
                .projectId(review.getBuildingPost().getId())
                .projectName(review.getBuildingPost().getTitle())
                .content(review.getContent())
                .reviewId(review.getId())
                .score(review.getScore())
                .build();
    }

    public Boolean executeUser(Long reportId, Long penalty) {
        ReviewReport reviewReport = reviewReportRepository.findById(reportId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        reviewReport.getReportedReview().deleteReview();
        reviewReport.getDefendant().updateUserState(UserState.PENALTY, LocalDate.now().plusDays(penalty));

        return true;
    }

    public Boolean expelUser(Long reportId) {
        ReviewReport reviewReport = reviewReportRepository.findById(reportId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        reviewReport.getReportedReview().deleteReview();
        reviewReport.getDefendant().updateUserState(UserState.EXPEL, LocalDate.now());

        return true;
    }

    public Boolean deleteReport(Long reportId) {
        ReviewReport reviewReport = reviewReportRepository.findByIdWithDefendant(reportId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        reviewReportRepository.delete(reviewReport);

        return true;
    }
}
