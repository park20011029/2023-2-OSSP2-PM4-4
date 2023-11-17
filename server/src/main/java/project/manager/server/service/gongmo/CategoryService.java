package project.manager.server.service.gongmo;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.gongmo.Category;
import project.manager.server.dto.reponse.gongmo.CategoryDto;
import project.manager.server.repository.gongmo.CategoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<CategoryDto> readCategory() {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();

        for (Category category : categoryList) {
            categoryDtos.add(CategoryDto.builder().category(category).build());
        }

        return categoryDtos;
    }
}
