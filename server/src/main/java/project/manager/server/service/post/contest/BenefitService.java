package project.manager.server.service.post.contest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.contest.Benefit;
import project.manager.server.dto.reponse.post.contest.BenefitDto;
import project.manager.server.dto.request.post.contest.PostTypeRequestDto;
import project.manager.server.repository.post.contest.BenefitRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BenefitService {

    private final BenefitRepository benefitRepository;

    public Boolean createBenefit(PostTypeRequestDto.BenefitDto benefitDto) {

        Benefit newBenefit = Benefit.builder().benefit(benefitDto.getBenefit()).build();
        benefitRepository.save(newBenefit);

        return true;
    }

    public Map<String, Object> readBenefitList() {

        List<Benefit> benefits = benefitRepository.findAll();

        return Collections.singletonMap("benefits", benefits.stream()
                .map(benefit -> BenefitDto.builder()
                        .benefit(benefit)
                        .build())
                .collect(Collectors.toList()));
    }

}
