package project.manager.server.dto.reponse.gongmo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.gongmo.Category;

@Getter
@NoArgsConstructor
public class CategoryDto {
    Category category;

    @Builder
    public CategoryDto(Category category) {
        this.category = category;
    }
}
