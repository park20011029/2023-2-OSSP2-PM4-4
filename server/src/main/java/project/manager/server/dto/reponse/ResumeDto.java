package project.manager.server.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.manager.server.domain.*;
import project.manager.server.enums.ResumeEduState;
import project.manager.server.enums.TechType;

@Getter
@AllArgsConstructor
public class ResumeDto {

    private final String job;
    private final Long userid;
    private final Long resumeId;
    private final String projectName;
    private final String projectGit;
    private final String projectDescription;
    private final String congress;
    private final Integer awardYear;
    private final String awardType;
    private final String school;
    private final Integer enroll;
    private final ResumeEduState resumeEduState;
    private final TechType techType;
    private final String techDescription;

    @Builder
    public ResumeDto(Resume resume, ResumeProject resumeProject, ResumeAward resumeAward, ResumeEdu resumeEdu, ResumeTech resumeTech){
        this.userid = resume.getUser().getId();
        this.resumeId = resume.getId();
        this.job = resume.getJob();
        this.projectName = resumeProject.getProjectName();
        this.projectGit = resumeProject.getProjectGit();
        this.projectDescription = resumeProject.getProjectDescription();
        this.congress = resumeAward.getCongress();
        this.awardYear = resumeAward.getAwardYear();
        this.awardType = resumeAward.getAwardType();
        this.school = resumeEdu.getSchool();
        this.enroll = resumeEdu.getEnroll();
        this.resumeEduState = resumeEdu.getResumeEduState();
        this.techType = resumeTech.getTechType();
        this.techDescription = resumeTech.getTechDescription();


    }
}
