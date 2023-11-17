package project.manager.server.service.gongmo;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.gongmo.Scale;
import project.manager.server.dto.reponse.gongmo.ScaleDto;
import project.manager.server.repository.gongmo.ScaleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ScaleService {

    private final ScaleRepository scaleRepository;

    @Transactional
    public List<ScaleDto> readScale() {
        List<ScaleDto> scaleDtos = new ArrayList<>();
        List<Scale> scaleList = scaleRepository.findAll();

        for (Scale scale : scaleList) {
            scaleDtos.add(ScaleDto.builder().scale(scale).build());
        }

        return scaleDtos;
    }
}
