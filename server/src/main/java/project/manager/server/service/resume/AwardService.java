package project.manager.server.service.resume;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.resume.Award;
import project.manager.server.dto.reponse.resume.AwardDto;
import project.manager.server.dto.request.resume.AwardRequestDto;
import project.manager.server.repository.resume.AwardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    @Transactional
    public Award createAward(AwardRequestDto awardRequestDto) {

        Award newAward =
                Award.builder()
                        .awardYear(awardRequestDto.getAwardYear())
                        .awardType(awardRequestDto.getAwardType())
                        .competition(awardRequestDto.getCompetition())
                        .build();

        return awardRepository.save(newAward);
    }

    public List<AwardDto> readAward(List<Award> awards) {

        List<AwardDto> awardDtos = new ArrayList<>();

        for (Award award : awards) {
            awardDtos.add(AwardDto.builder().award(award).build());
        }

        return awardDtos;
    }
}
