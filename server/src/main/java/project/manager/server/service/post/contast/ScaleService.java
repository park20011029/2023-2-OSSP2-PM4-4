package project.manager.server.service.post.contast;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.contest.Scale;
import project.manager.server.dto.reponse.post.contest.ScaleDto;
import project.manager.server.repository.post.contast.ScaleRepository;

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
