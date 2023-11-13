package project.manager.server.dto.request.resume;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AwardRequestDto {

    @NotNull(message = "[Award Request] Competition name can not be null")
    private String competition;

    @NotNull(message = "[Award Request] Award type can not be null")
    private String awardType;

    @NotNull(message = "[Award Request] Award year can not be null")
    private LocalDate awardYear;
}
