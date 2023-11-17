package project.manager.server.service.gongmo;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.gongmo.Benefit;
import project.manager.server.dto.reponse.gongmo.BenefitDto;
import project.manager.server.repository.gongmo.BenefitRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BenefitService {

    private final BenefitRepository benefitRepository;

    public List<BenefitDto> readBenefit() {
        List<BenefitDto> benefitDtos = new ArrayList<>();
        List<Benefit> benefitDtoList = benefitRepository.findAll();

        for (Benefit benefit : benefitDtoList) {
            benefitDtos.add(BenefitDto.builder().benefit(benefit).build());
        }

        return benefitDtos;
    }
}
