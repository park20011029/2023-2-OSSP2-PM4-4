package project.manager.server.dto.request.resume.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeUpdateDto {

    @NotNull(message = "[Resume Update] Gu id can not be null")
    private Long guId;

    @NotNull(message = "[Resume Update] Job can not be null")
    private String job;

    @NotNull(message = "[Resume Update] Birth can not be null")
    private LocalDate birth;

    @NotNull(message = "[Resume Update] Gender can not be null")
    private boolean gender;

    @Valid
    @NotNull(message = "[Resume Update] SchoolInfo can not be null")
    private SchoolUpdateDto schoolInfo;

    @Valid
    private List<ProjectUpdateDto> projects;

    @Valid
    private List<AwardUpdateDto> awards;

    @Valid
    private List<TechStackUpdateDto> techStacks;
}
