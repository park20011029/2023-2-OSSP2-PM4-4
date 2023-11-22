package project.manager.server.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.post.contest.Scale;
import project.manager.server.dto.reponse.post.contest.ScaleDto;
import project.manager.server.dto.reponse.post.contest.TargetDto;
import project.manager.server.dto.request.post.contest.ScaleRequestDto;
import project.manager.server.repository.post.contast.ScaleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScaleService {

    private final ScaleRepository scaleRepository;

    public Boolean createScale(String scale) {

        Scale newScale = Scale.builder().scale(scale).build();
        scaleRepository.save(newScale);

        return true;
    }

    public Map<String, Object> readScaleList() {

        List<Scale> scales = scaleRepository.findAll();
        return Collections.singletonMap("scales", scales.stream()
                .map(scale -> ScaleDto.builder()
                        .scale(scale)
                        .build())
                .collect(Collectors.toList()));
    }
}
