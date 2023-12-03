package project.manager.server.service.resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.*;
import project.manager.server.domain.region.Gu;
import project.manager.server.domain.resume.*;
import project.manager.server.dto.reponse.resume.*;
import project.manager.server.dto.request.resume.create.ResumeRequestDto;
import project.manager.server.dto.request.resume.update.ResumeUpdateDto;
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
    private final SchoolRepository schoolRepository;
    private final ProjectRepository projectRepository;
    private final AwardRepository awardRepository;
    private final TechStackRepository techStackRepository;

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

        resumeRepository.save(newResume);

        resumeRequestDto.getAwards()
                .forEach(awardRequestDto ->
                        awardRepository.save(Award.builder()
                                .resume(newResume)
                                .awardYear(awardRequestDto.getAwardYear())
                                .awardType(awardRequestDto.getAwardType())
                                .competition(awardRequestDto.getCompetition())
                                .build()));

        resumeRequestDto.getProjects()
                .forEach(projectRequestDto ->
                        projectRepository.save(Project.builder()
                                .resume(newResume)
                                .projectName(projectRequestDto.getProjectName())
                                .gitUrl(projectRequestDto.getGitUrl())
                                .description(projectRequestDto.getDescription())
                                .build()));

        resumeRequestDto.getTechStacks()
                        .forEach(techStackRequestDto ->
                                techStackRepository.save(TechStack.builder()
                                        .resume(newResume)
                                        .tech(techStackRequestDto.getTech())
                                        .techType(techStackRequestDto.getTechType())
                                        .description(techStackRequestDto.getDescription())
                                        .build()));

        schoolRepository.save(School.builder()
                .resume(newResume)
                .schoolRegister(resumeRequestDto.getSchoolInfo().getSchoolRegister())
                .major(resumeRequestDto.getSchoolInfo().getMajor())
                .name(resumeRequestDto.getSchoolInfo().getName())
                .build());

        return newResume.getId();
    }


    public Map<String, Object> readResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));
        Map<String, Object> result = new HashMap<>();

        result.put("resume", ResumeDto.builder()
                .resume(resume)
                .build());

        result.put("srojects", projectRepository.findByResumeId(resumeId)
                .stream().map(project ->
                        ProjectDto.builder()
                                .project(project)
                                .build())
                .collect(Collectors.toList()));

        result.put("awards", awardRepository.findByResumeId(resumeId)
                .stream().map(award ->
                        AwardDto.builder()
                                .award(award)
                                .build())
                .collect(Collectors.toList()));

        result.put("techStacks", techStackRepository.findByResumeId(resumeId)
                .stream().map(techStack ->
                        TechStackDto.builder()
                                .techStack(techStack)
                                .build())
                .collect(Collectors.toList()));

        result.put("schoolInfo", SchoolDto.builder()
                .school(schoolRepository.findByResumeId(resumeId)
                        .orElseThrow(() ->
                                new ApiException(ErrorDefine.ENTITY_NOT_FOUND)))
                .build());

        return result;
    }


    public boolean updateResume(Long resumeId, ResumeUpdateDto resumeUpdateDto) {
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        Gu gu = guRepository.findById(resumeUpdateDto.getGuId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        resume.updateResume(
                resumeUpdateDto.getJob(),
                resumeUpdateDto.isGender(),
                resumeUpdateDto.getBirth(),
                gu);

//        resumeUpdateDto.getAwards()
//                .forEach(awardUpdateDto ->
//                        awardRepository.findById(awardUpdateDto.getId())
//                                .orElseThrow(() ->
//                                        new ApiException(ErrorDefine.ENTITY_NOT_FOUND))
//                                .updateAward(
//                                        awardUpdateDto.getCompetition(),
//                                        awardUpdateDto.getAwardYear(),
//                                        awardUpdateDto.getAwardType()));
//
//        resumeUpdateDto.getProjects()
//                .forEach(projectUpdateDto ->
//                        projectRepository.findById(projectUpdateDto.getId())
//                                .orElseThrow(() ->
//                                        new ApiException(ErrorDefine.ENTITY_NOT_FOUND))
//                                .updateProject(
//                                        projectUpdateDto.getProjectName(),
//                                        projectUpdateDto.getGitUrl(),
//                                        projectUpdateDto.getDescription()));
//
//        resumeUpdateDto.getTechStacks()
//                .forEach(techStackUpdateDto ->
//                        techStackRepository.findById(techStackUpdateDto.getId())
//                                .orElseThrow(() ->
//                                        new ApiException(ErrorDefine.ENTITY_NOT_FOUND))
//                                .updateTech(
//                                        techStackUpdateDto.getTech(),
//                                        techStackUpdateDto.getTechType(),
//                                        techStackUpdateDto.getDescription()));

        schoolRepository.findById(resumeUpdateDto.getSchoolInfo().getId())
                .orElseThrow(() ->
                        new ApiException(ErrorDefine.ENTITY_NOT_FOUND))
                .updateSchool(
                        resumeUpdateDto.getSchoolInfo().getName(),
                        resumeUpdateDto.getSchoolInfo().getMajor(),
                        resumeUpdateDto.getSchoolInfo().getSchoolRegister());

        return true;
    }

}
