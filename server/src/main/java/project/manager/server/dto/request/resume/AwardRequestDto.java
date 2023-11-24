package project.manager.server.dto.request.resume;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AwardRequestDto {
    private Long id;

    @NotNull(message = "[Award Request] Competition name can not be null")
    private String competition;

    @NotNull(message = "[Award Request] Award type can not be null")
    private String awardType;

    @NotNull(message = "[Award Request] Award year can not be null")
    private LocalDate awardYear;
}
