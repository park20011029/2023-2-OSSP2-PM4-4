package project.manager.server.dto.request.post.contest;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    @NotNull
    private String id;
    @NotNull
    private String category;

}
