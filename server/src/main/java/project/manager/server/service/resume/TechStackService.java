package project.manager.server.service.resume;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.resume.Resume;
import project.manager.server.domain.resume.TechStack;
import project.manager.server.dto.request.resume.create.TechStackRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.ResumeRepository;
import project.manager.server.repository.resume.TechStackRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackRepository techStackRepository;
    private final ResumeRepository resumeRepository;

    public Long addTechStack(Long resumeId, TechStackRequestDto techStackRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        TechStack newTechStack = TechStack.builder()
                .resume(resume)
                .tech(techStackRequestDto.getTech())
                .techType(techStackRequestDto.getTechType())
                .description(techStackRequestDto.getDescription())
                .build();
        techStackRepository.save(newTechStack);

        return newTechStack.getId();
    }

    public boolean deleteTechStack(Long techStackId) {
        TechStack techStack = techStackRepository.findById(techStackId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        techStackRepository.delete(techStack);

        return true;
    }
}
