package project.manager.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.*;
import project.manager.server.dto.reponse.ResumeDto;
import project.manager.server.dto.reponse.UserDto;
import project.manager.server.dto.request.ResumeRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.*;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ResumeProjectRepository resumeProjectRepository;
    private final ResumeAwardRepository resumeAwardRepository;
    private final ResumeEduRepository resumeEduRepository;
    private final ResumeTechRepository resumeTechRepository;
    private final UserRepository userRepository;


   @Transactional
    public ResumeDto createResume(ResumeRequestDto resumeRequestDto){
       if(resumeRepository.existsByUserId(resumeRequestDto.getUserId())){
           throw new ApiException((ErrorDefine.RESUME_EXIST));
       }
       User user = userRepository.getReferenceById(resumeRequestDto.getUserId());
       Resume newResume = Resume.builder().user(user).job(resumeRequestDto.getJob()).build();
       Resume saveResume = resumeRepository.save(newResume);

       Resume resumeId = resumeRepository.getReferenceById(saveResume.getId());
       ResumeProject newResumeProject = ResumeProject.builder().resume(resumeId).projectName(resumeRequestDto.getProjectName()).projectGit(resumeRequestDto.getProjectGit()).projectDescription(resumeRequestDto.getProjectDescription()).build();
       resumeProjectRepository.save(newResumeProject);

       ResumeAward newResumeAward = ResumeAward.builder().resume(resumeId).congress(resumeRequestDto.getCongress()).awardYear(resumeRequestDto.getAwardYear()).awardType(resumeRequestDto.getAwardType()).build();
       resumeAwardRepository.save(newResumeAward);

       ResumeEdu newResumeEdu = ResumeEdu.builder().resume(resumeId).school(resumeRequestDto.getSchool()).enroll(resumeRequestDto.getEnroll()).resumeEduState(resumeRequestDto.getResumeEduState()).build();
       resumeEduRepository.save(newResumeEdu);

       ResumeTech newResumeTech = ResumeTech.builder().resume(resumeId).techType(resumeRequestDto.getTechType()).techDescription(resumeRequestDto.getTechDescription()).build();
       resumeTechRepository.save(newResumeTech);

       return ResumeDto.builder().resume(newResume).resumeProject(newResumeProject).resumeAward(newResumeAward).resumeEdu(newResumeEdu).resumeTech(newResumeTech).build();

   }
    @Transactional
    public ResumeDto updateResume(User user, ResumeRequestDto resumeRequestDto){
       Resume resume =
               resumeRepository
                       .findByUserId(user.getId())
                       .orElseThrow(() -> new ApiException((ErrorDefine.RESUME_NOT_FOUND) ));
       user = userRepository.getReferenceById(resumeRequestDto.getUserId());
       resume.updateResume(user,resumeRequestDto.getJob());

       Resume resumeId = resumeRepository.getReferenceById(resume.getId());
       ResumeAward updateResumeAward = new ResumeAward();
       updateResumeAward.updateResumeAward(resumeId,resumeRequestDto.getCongress(), resumeRequestDto.getAwardYear(), resumeRequestDto.getAwardType());


        ResumeProject updateResumeProject = new ResumeProject();
        updateResumeProject.updateResumeProject(resumeId,resumeRequestDto.getProjectName(),resumeRequestDto.getProjectGit(),resumeRequestDto.getProjectDescription());

        ResumeEdu updateResumeEdu = new ResumeEdu();
        updateResumeEdu.updateResumeEdu(resumeId,resumeRequestDto.getSchool(),resumeRequestDto.getEnroll(),resumeRequestDto.getResumeEduState());

        ResumeTech updateResumeTech = new ResumeTech();
        updateResumeTech.updateResumeTech(resumeId,resumeRequestDto.getTechType(),resumeRequestDto.getTechDescription());

        return ResumeDto.builder().resume(resume).resumeProject(updateResumeProject).resumeAward(updateResumeAward).resumeEdu(updateResumeEdu).resumeTech(updateResumeTech).build();

    }
}


