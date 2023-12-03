package project.manager.server.dto.reponse.resume;

import lombok.Builder;
import lombok.NoArgsConstructor;

import project.manager.server.domain.resume.School;

@NoArgsConstructor
public class SchoolDto {
    Long id;
    String name;
    String schoolRegister;
    String major;

    @Builder
    public SchoolDto(School school) {
        this.id = school.getId();
        this.name = school.getName();
        this.major = school.getMajor();
        this.schoolRegister = school.getSchoolRegister().getToKorean();
    }
}
