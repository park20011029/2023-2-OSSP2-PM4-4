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

    @Transactional
    public Long createResume(Long userId, ResumeRequestDto resumeRequestDto) {

        if (resumeRepository.existsByUserId(userId)) {
            throw new ApiException((ErrorDefine.RESUME_EXIST));
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Resume newResume = Resume.builder()
                .job(resumeRequestDto.getJob())
                .gender(resumeRequestDto.isGender())
                .build();

        Gu gu = guRepository.findById(resumeRequestDto.getGuId()).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        newResume.setGu(gu);

        for (AwardRequestDto awardRequestDto : resumeRequestDto.getAwards()) {
            newResume.addAward(awardService.createAward(awardRequestDto));
        }

        for (ProjectRequestDto projectRequestDto : resumeRequestDto.getProjects()) {
            newResume.addProject(projectService.createProject(projectRequestDto));
        }

        for (TechStackRequestDto techStackRequestDto : resumeRequestDto.getTechStacks()) {
            newResume.addTechStack(techStackService.createTechStack(techStackRequestDto));
        }

        newResume.addSchool(schoolService.createSchool(resumeRequestDto.getSchoolInfo()));

        user.addResume(newResume);

        resumeRepository.save(newResume);

        return newResume.getId();

    }

    public ResumeDto readResume(Long userId) {
        Resume resume = resumeRepository.findByUserId(userId).orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        return ResumeDto.builder()
                .resume(resume)
                .awards(awardService.readAward(resume.getAwards()))
                .projects(projectService.readProject(resume.getProjects()))
                .techStacks(techStackService.readTechStack(resume.getTechStacks()))
                .build();
    }

//    @Transactional
//    public ResumeDto updateResume(User user, ResumeRequestDto resumeRequestDto) {
//        Resume resume =
//                resumeRepository
//                        .findByUserId(user.getId())
//                        .orElseThrow(() -> new ApiException((ErrorDefine.RESUME_NOT_FOUND)));
//        user = userRepository.getReferenceById(resumeRequestDto.getUserId());
//        resume.updateResume(user, resumeRequestDto.getJob());
//
//        Resume resumeId = resumeRepository.getReferenceById(resume.getId());
//        Award updateResumeAward = new Award();
//        updateResumeAward.updateResumeAward(
//                resumeId,
//                resumeRequestDto.getCongress(),
//                resumeRequestDto.getAwardYear(),
//                resumeRequestDto.getAwardType());
//
//        Project updateResumeProject = new Project();
//        updateResumeProject.updateResumeProject(
//                resumeId,
//                resumeRequestDto.getProjectName(),
//                resumeRequestDto.getProjectGit(),
//                resumeRequestDto.getProjectDescription());
//
//        ResumeEdu updateResumeEdu = new ResumeEdu();
//        updateResumeEdu.updateResumeEdu(
//                resumeId,
//                resumeRequestDto.getSchool(),
//                resumeRequestDto.getEnroll(),
//                resumeRequestDto.getResumeEduState());
//
//        TechStack updateResumeTech = new TechStack();
//        updateResumeTech.updateResumeTech(
//                resumeId, resumeRequestDto.getTechType(), resumeRequestDto.getTechDescription());
//
//        return ResumeDto.builder()
//                .resume(resume)
//                .resumeProject(updateResumeProject)
//                .resumeAward(updateResumeAward)
//                .resumeEdu(updateResumeEdu)
//                .resumeTech(updateResumeTech)
//                .build();
//    }
}
