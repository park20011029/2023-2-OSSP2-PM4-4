package project.manager.server.service.gongmo;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.gongmo.Target;
import project.manager.server.domain.gongmo.Target;
import project.manager.server.dto.reponse.gongmo.TargetDto;
import project.manager.server.repository.gongmo.TargetRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    @Transactional
    public List<TargetDto> readTarget() {
        List<TargetDto> targetDtos = new ArrayList<>();
        List<Target> targetList = targetRepository.findAll();

        for (Target target : targetList) {
            targetDtos.add(TargetDto.builder().target(target).build());
        }

        return targetDtos;
    }
}
