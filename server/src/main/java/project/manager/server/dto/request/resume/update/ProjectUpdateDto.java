package project.manager.server.dto.request.resume.update;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateDto {
    @NotNull(message = "[Project Update] Id can not be null")
    private Long id;

    @NotNull(message = "[Project Update] Github url can not be null")
    private String gitUrl;

    @NotNull(message = "[Project Update] Project description can not be null")
    private String description;

    @NotNull(message = "[Project Update] Project name can not be null")
    private String projectName;
}
