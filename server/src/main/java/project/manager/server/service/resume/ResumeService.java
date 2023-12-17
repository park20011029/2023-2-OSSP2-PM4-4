package project.manager.server.service.resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import project.manager.server.util.S3UploadUtil;

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
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;

    // Create
    public Long createResume(Long userId, ResumeRequestDto resumeRequestDto, MultipartFile file) {

        if (resumeRepository.existsByUserId(userId)) {
            throw new ApiException((ErrorDefine.RESUME_EXIST));
        }

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Gu gu = guRepository.findById(resumeRequestDto.getGuId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        String url = s3UploadUtil.upload(file, "pm4/");

        Image newImage = Image.builder()
                .url(url)
                .uploader(user)
                .build();
        imageRepository.save(newImage);

        Resume newResume = Resume.builder()
                        .gu(gu)
                        .birth(resumeRequestDto.getBirth())
                        .user(user)
                        .job(resumeRequestDto.getJob())
                        .gender(resumeRequestDto.isGender())
                        .build();

        resumeRepository.save(newResume);

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
                .schoolImage(newImage)
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

        result.put("projects", projectRepository.findByResumeId(resumeId)
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


    public Boolean updateResume(Long resumeId, ResumeUpdateDto resumeUpdateDto) {
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

        return true;
    }

}
