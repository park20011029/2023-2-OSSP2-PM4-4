package project.manager.server.dto.request.resume;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.enums.SchoolRegister;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRequestDto {
    private Long id;

    @NotNull(message = "[School Request] School name can not be null")
    String name;

    @NotNull(message = "[School Request] Register can not be null")
    SchoolRegister schoolRegister;
}
