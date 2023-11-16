package project.manager.server.dto.reponse.resume;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.manager.server.domain.resume.*;
import project.manager.server.dto.reponse.region.GuDto;
import project.manager.server.json.NullToNullStringSerializer;

@Getter
@AllArgsConstructor
public class ResumeDto {

    private Long resumeId;
    private String name;
    private LocalDate birth;
    private String job;
    private boolean gender;
    private GuDto gu;
    private String si;
    private SchoolDto schoolInfo;

    @JsonSerialize(using = NullToNullStringSerializer.class)
    private List<ProjectDto> projects;

    @JsonSerialize(using = NullToNullStringSerializer.class)
    private List<AwardDto> awards;

    @JsonSerialize(using = NullToNullStringSerializer.class)
    private List<TechStackDto> techStacks;

    @Builder
    public ResumeDto(Resume resume, List<AwardDto> awards, List<ProjectDto> projects, List<TechStackDto> techStacks) {
        this.resumeId = resume.getId();
        this.name = resume.getUser().getName();
        this.birth = resume.getBirth();
        this.job = resume.getJob();
        this.gender = resume.isGender();
        this.gu = GuDto.builder().gu(resume.getGu()).build();
        this.si = resume.getGu().getSi().getSi();
        this.projects = projects;
        this.awards = awards;
        this.techStacks = techStacks;
        this.schoolInfo = SchoolDto.builder().school(resume.getSchool()).build();
    }
}
