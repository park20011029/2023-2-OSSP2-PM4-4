package project.manager.server.service.resume;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.resume.TechStack;
import project.manager.server.dto.reponse.resume.TechStackDto;
import project.manager.server.dto.request.resume.TechStackRequestDto;
import project.manager.server.repository.resume.TechStackRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackRepository techStackRepository;

    @Transactional
    public TechStack createTechStack(TechStackRequestDto techStackRequestDto) {

        TechStack newTechStack =
                TechStack.builder()
                        .tech(techStackRequestDto.getTech())
                        .techType(techStackRequestDto.getTechType())
                        .description(techStackRequestDto.getDescription())
                        .build();

        return techStackRepository.save(newTechStack);
    }

    public List<TechStackDto> readTechStack(List<TechStack> techStacks) {

        List<TechStackDto> techStackDtos = new ArrayList<>();

        for (TechStack techStack : techStacks) {
            techStackDtos.add(TechStackDto.builder().techStack(techStack).build());
        }

        return techStackDtos;
    }
}
