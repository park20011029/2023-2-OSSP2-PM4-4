package project.manager.server.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.post.contest.Target;
import project.manager.server.dto.reponse.post.contest.ContestTitleDto;
import project.manager.server.dto.reponse.post.contest.TargetDto;
import project.manager.server.dto.request.post.contest.TargetRequestDto;
import project.manager.server.repository.post.contast.TargetRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    public Boolean createTarget(String target) {

        Target newTarget = Target.builder().target(target).build();
        targetRepository.save(newTarget);

        return true;
    }

    public Map<String, Object> readTargetList() {

        List<Target> targets = targetRepository.findAll();

        return Collections.singletonMap("targets", targets.stream()
                .map(target -> TargetDto.builder()
                        .target(target)
                        .build())
                .collect(Collectors.toList()));
    }
}