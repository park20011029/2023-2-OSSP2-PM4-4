package project.manager.server.dto.reponse.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.resume.School;

@Getter
@NoArgsConstructor
public class SchoolDto {
    Long id;
    String name;
    String schoolRegister;

    @Builder
    public SchoolDto(School school) {
        this.id = school.getId();
        this.name = school.getName();
        this.schoolRegister = school.getSchoolRegister().getToKorean();
    }
}
