package project.manager.server.dto.reponse.post.building;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuildingPostDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long userId;
    @NotNull
    private String user;
    @NotNull
    private LocalDate creatAt;
}
