package project.manager.server.service.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.*;
import project.manager.server.domain.region.Gu;
import project.manager.server.domain.resume.*;
import project.manager.server.dto.reponse.resume.*;
import project.manager.server.dto.request.resume.AwardRequestDto;
import project.manager.server.dto.request.resume.ProjectRequestDto;
import project.manager.server.dto.request.resume.ResumeRequestDto;
import project.manager.server.dto.request.resume.TechStackRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.*;
import project.manager.server.repository.region.GuRepository;
import project.manager.server.repository.resume.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final GuRepository guRepository;
    private final ResumeRepository resumeRepository;
    private final SchoolService schoolService;
    private final AwardService awardService;
    private final TechStackService techStackService;
    private final ProjectService projectService;

    // Create
    public Long createResume(Long userId, ResumeRequestDto resumeRequestDto) {

        if (resumeRepository.existsByUserId(userId)) {
            throw new ApiException((ErrorDefine.RESUME_EXIST));
        }

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Gu gu = guRepository.findById(resumeRequestDto.getGuId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Resume newResume = Resume.builder()
                        .gu(gu)
                        .birth(resumeRequestDto.getBirth())
                        .user(user)
                        .job(resumeRequestDto.getJob())
                        .gender(resumeRequestDto.isGender())
                        .build();

        if (resumeRequestDto.getAwards() != null) {
            for (AwardRequestDto awardRequestDto : resumeRequestDto.getAwards()) {
                awardService.createAward(awardRequestDto, newResume);
            }
        }

        if (resumeRequestDto.getProjects() != null) {
            for (ProjectRequestDto projectRequestDto : resumeRequestDto.getProjects()) {
                projectService.createProject(projectRequestDto, newResume);
            }
        }

        if (resumeRequestDto.getTechStacks() != null) {
            for (TechStackRequestDto techStackRequestDto : resumeRequestDto.getTechStacks()) {
                techStackService.createTechStack(techStackRequestDto, newResume);
            }
        }

        schoolService.createSchool(resumeRequestDto.getSchoolInfo(), newResume);

        resumeRepository.save(newResume);

        return newResume.getId();
    }

    // Read
    public ResumeDto readResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        return ResumeDto.builder()
                .resume(resume)
                .awards(awardService.readAward(resume.getAwards()))
                .projects(projectService.readProject(resume.getProjects()))
                .techStacks(techStackService.readTechStack(resume.getTechStacks()))
                .build();
    }

    // Update
    public Long addProject(Long resumeId, ProjectRequestDto projectRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        return projectService.createProject(projectRequestDto, resume).getId();
    }

    public Long addTechStack(Long resumeId, TechStackRequestDto techStackRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        return techStackService.createTechStack(techStackRequestDto, resume).getId();
    }

    public Long addAward(Long resumeId, AwardRequestDto awardRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        return awardService.createAward(awardRequestDto, resume).getId();
    }

    public boolean updateResume(Long resumeId, ResumeRequestDto resumeRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        Gu gu = guRepository.findById(resumeRequestDto.getGuId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        resume.updateResume(
                resumeRequestDto.getJob(),
                resumeRequestDto.isGender(),
                resumeRequestDto.getBirth(),
                gu);

        for (AwardRequestDto awardRequestDto : resumeRequestDto.getAwards()) {
            if (awardRequestDto.getId() != null) {
                awardService.updateAward(awardRequestDto);
            } else {
                throw new ApiException(ErrorDefine.ENTITY_NOT_FOUND);
            }
        }

        for (ProjectRequestDto projectRequestDto : resumeRequestDto.getProjects()) {
            if (projectRequestDto.getId() != null) {
                projectService.updateProject(projectRequestDto);
            } else {
                throw new ApiException(ErrorDefine.ENTITY_NOT_FOUND);
            }
        }

        for (TechStackRequestDto techStackRequestDto : resumeRequestDto.getTechStacks()) {
            if (techStackRequestDto.getId() != null) {
                techStackService.updateTechStack(techStackRequestDto);
            } else {
                throw new ApiException(ErrorDefine.ENTITY_NOT_FOUND);
            }
        }

        if (resumeRequestDto.getSchoolInfo().getId() != null) {
            schoolService.updateSchool(resumeRequestDto.getSchoolInfo());
        } else {
            throw new ApiException(ErrorDefine.ENTITY_NOT_FOUND);
        }

        return true;
    }

}
