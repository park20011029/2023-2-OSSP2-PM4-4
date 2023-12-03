package project.manager.server.service.resume;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.resume.Award;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.request.resume.create.AwardRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.AwardRepository;
import project.manager.server.repository.resume.ResumeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;
    private final ResumeRepository resumeRepository;

    public Long addAward(Long resumeId, AwardRequestDto awardRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        Award newAward = Award.builder()
                .resume(resume)
                .awardYear(awardRequestDto.getAwardYear())
                .awardType(awardRequestDto.getAwardType())
                .competition(awardRequestDto.getCompetition())
                .build();
        awardRepository.save(newAward);

        return newAward.getId();
    }

    public boolean deleteAward(Long awardId) {
        Award award = awardRepository
                        .findById(awardId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        awardRepository.delete(award);

        return true;
    }
}
