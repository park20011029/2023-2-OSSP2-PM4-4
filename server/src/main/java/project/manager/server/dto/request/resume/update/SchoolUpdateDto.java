package project.manager.server.dto.request.resume.update;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.SchoolRegister;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolUpdateDto {
    @NotNull(message = "[School Update] Id can not be null")
    private Long id;

    @NotNull(message = "[School Update] User id can not be null")
    private Long userId;

    @NotNull(message = "[School Update] School name can not be null")
    String name;

    @NotNull(message = "[School Update] School major can not be null")
    String major;

    @NotNull(message = "[School Update] Register can not be null")
    SchoolRegister schoolRegister;
}
