package project.manager.server.dto.request.resume.update;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.TechType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TechStackUpdateDto {
    @NotNull(message = "[TechStack Update] Id can not be null")
    private Long id;

    @NotNull(message = "[TechStack Update] Tech type can not be null")
    TechType techType;

    @NotNull(message = "[TechStack Update] Tech description can not be null")
    String description;

    @NotNull(message = "[TechStack Update] Tech can not be null")
    String tech;
}
