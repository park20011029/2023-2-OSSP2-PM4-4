package project.manager.server.service.post.contest;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.contest.Target;
import project.manager.server.dto.reponse.post.contest.TargetDto;
import project.manager.server.dto.request.post.contest.PostTypeRequestDto;
import project.manager.server.repository.post.contest.TargetRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    public Boolean createTarget(PostTypeRequestDto.TargetDto targetDto) {

        Target newTarget = Target.builder().target(targetDto.getTarget()).build();
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
