package project.manager.server.service.resume;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.resume.Resume;
import project.manager.server.domain.resume.TechStack;
import project.manager.server.dto.reponse.resume.TechStackDto;
import project.manager.server.dto.request.resume.TechStackRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.TechStackRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackRepository techStackRepository;

    public TechStack createTechStack(TechStackRequestDto techStackRequestDto, Resume resume) {

        TechStack newTechStack = TechStack.builder()
                        .resume(resume)
                        .tech(techStackRequestDto.getTech())
                        .techType(techStackRequestDto.getTechType())
                        .description(techStackRequestDto.getDescription())
                        .build();

        return techStackRepository.save(newTechStack);
    }

    public List<TechStackDto> readTechStack(List<TechStack> techStacks) {

        return Optional.ofNullable(techStacks)
                .map(t -> t.stream()
                        .map(techStack -> TechStackDto.builder()
                                .techStack(techStack)
                                .build())
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public void updateTechStack(TechStackRequestDto techStackRequestDto) {
        TechStack techStack = techStackRepository.findById(techStackRequestDto.getId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        techStack.updateTech(techStackRequestDto);
    }

    public boolean deleteTechStack(Long techStackId) {
        TechStack techStack = techStackRepository.findById(techStackId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        techStackRepository.delete(techStack);

        return true;
    }
}
