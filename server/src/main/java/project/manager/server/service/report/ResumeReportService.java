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

import project.manager.server.domain.User;
import project.manager.server.domain.report.ResumeReport;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.reponse.post.PageInfo;
import project.manager.server.dto.reponse.report.ReportTitle;
import project.manager.server.dto.request.report.ResumeReportRequestDto;
import project.manager.server.enums.UserState;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.report.ResumeReportRepository;
import project.manager.server.repository.resume.ResumeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeReportService {

    private final ResumeReportRepository resumeReportRepository;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    //신고 시점 데이터 받아오기
    /** */
    public Boolean creatBuildingReport(ResumeReportRequestDto resumeReportRequestDto) {
        User reporter = userRepository.findById(resumeReportRequestDto.getReporterId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        User defendant = userRepository.findById(resumeReportRequestDto.getDefendantId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        Resume resume = resumeRepository.findById(resumeReportRequestDto.getResumeId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        ResumeReport newResumeReport = ResumeReport.builder()
                .reporter(reporter)
                .defendant(defendant)
                .reportReason(resumeReportRequestDto.getReportReason())
                .description(resumeReportRequestDto.getDescription())
                .reportedResume(resume)
                .build();

        resumeReportRepository.save(newResumeReport);

        return true;
    }

    public Map<String, Object> readReportList(Integer page, Integer size) {

        Page<ResumeReport> reports = resumeReportRepository.findAll(PageRequest.of(page, size, Sort.by("createAt").descending()));

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
                        .contentId(report.getReportedResume().getId())
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

    public Boolean executeUser(Long reportId, Long penalty) {
        ResumeReport resumeReport = resumeReportRepository.findByIdWithDefendant(reportId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        resumeReport.getDefendant().updateUserState(UserState.PENALTY, LocalDate.now().plusDays(penalty));

        return true;
    }

    public Boolean expelUser(Long reportId) {
        ResumeReport resumeReport = resumeReportRepository.findByIdWithDefendant(reportId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        resumeReport.getDefendant().updateUserState(UserState.EXPEL, LocalDate.now());

        return true;
    }

    public Boolean deleteReport(Long reportId) {
        ResumeReport resumeReport = resumeReportRepository.findByIdWithDefendant(reportId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        resumeReportRepository.delete(resumeReport);

        return true;
    }
}
