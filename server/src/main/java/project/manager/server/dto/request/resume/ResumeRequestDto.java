package project.manager.server.dto.request.resume;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeRequestDto {

    @NotNull(message = "[Resume Request] Gu id can not be null")
    private Long guId;

    @NotNull(message = "[Resume Request] Job can not be null")
    private String job;

    @NotNull(message = "[Resume Request] Birth can not be null")
    private LocalDate birth;

    @NotNull(message = "[Resume Request] Gender can not be null")
    private boolean gender;
    private List<ProjectRequestDto> projects;
    private List<AwardRequestDto> awards;
    private List<TechStackRequestDto> techStacks;
    private SchoolRequestDto schoolInfo;
}
