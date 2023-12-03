package project.manager.server.dto.request.resume.create;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.TechType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TechStackRequestDto {

    @NotNull(message = "[TechStack Request] Tech type can not be null")
    TechType techType;

    @NotNull(message = "[TechStack Request] Tech description can not be null")
    String description;

    @NotNull(message = "[TechStack Request] Tech can not be null")
    String tech;
}
