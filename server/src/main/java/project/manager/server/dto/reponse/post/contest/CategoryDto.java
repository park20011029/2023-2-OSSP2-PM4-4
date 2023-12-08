package project.manager.server.dto.reponse.post.contest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.post.contest.Category;

@Getter
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String category;

    @Builder
    public CategoryDto(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
