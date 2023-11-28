package project.manager.server.dto.request.post.building;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.enums.TechType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartRequestDto {
    @NotNull
    private TechType techType;
    @NotNull
    private String partName;
    @NotNull
    private Integer maxApplicant;
}
