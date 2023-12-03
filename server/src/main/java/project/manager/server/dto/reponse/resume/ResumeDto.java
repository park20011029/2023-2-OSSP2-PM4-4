package project.manager.server.dto.reponse.resume;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import project.manager.server.domain.resume.*;
import project.manager.server.dto.reponse.region.GuDto;
import project.manager.server.dto.reponse.region.SiDto;

//수정 요함
@Getter
@AllArgsConstructor
public class ResumeDto {

    private Long resumeId;
    private String name;
    private LocalDate birth;
    private String job;
    private boolean gender;
    private GuDto gu;
    private SiDto si;
    @Builder
    public ResumeDto(Resume resume) {
        this.resumeId = resume.getId();
        this.name = resume.getUser().getName();
        this.birth = resume.getBirth();
        this.job = resume.getJob();
        this.gender = resume.isGender();
        this.gu = GuDto.builder().gu(resume.getGu()).build();
        this.si = SiDto.builder().si(resume.getGu().getSi()).build();
    }
}
