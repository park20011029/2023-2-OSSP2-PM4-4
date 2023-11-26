package project.manager.server.dto.request.post.building;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartRequestDto {
    @NotNull
    private String partName;
    @NotNull
    private Integer maxNum;
}
