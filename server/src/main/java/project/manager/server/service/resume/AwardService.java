package project.manager.server.service.resume;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.resume.Award;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.reponse.resume.AwardDto;
import project.manager.server.dto.request.resume.AwardRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.AwardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    public Award createAward(AwardRequestDto awardRequestDto, Resume resume) {

        Award newAward = Award.builder()
                        .resume(resume)
                        .awardYear(awardRequestDto.getAwardYear())
                        .awardType(awardRequestDto.getAwardType())
                        .competition(awardRequestDto.getCompetition())
                        .build();

        return awardRepository.save(newAward);
    }

    public List<AwardDto> readAward(List<Award> awards) {

        return Optional.ofNullable(awards)
                .map(a -> a.stream()
                        .map(award -> AwardDto.builder().award(award).build())
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public void updateAward(AwardRequestDto awardRequestDto) {
        Award award = awardRepository.findById(awardRequestDto.getId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        award.updateAward(
                awardRequestDto.getCompetition(),
                awardRequestDto.getAwardYear(),
                awardRequestDto.getAwardType());
    }

    public boolean deleteAward(Long awardId) {
        Award award = awardRepository
                        .findById(awardId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        awardRepository.delete(award);

        return true;
    }
}
