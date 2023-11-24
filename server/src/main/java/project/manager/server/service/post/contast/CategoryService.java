package project.manager.server.service.post.contast;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.contest.Category;
import project.manager.server.dto.reponse.post.contest.CategoryDto;
import project.manager.server.repository.post.contast.CategoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Boolean createCategory(String category) {

        Category newCategory = Category.builder().category(category).build();
        categoryRepository.save(newCategory);

        return true;
    }

    public Map<String, Object> readCategoryList() {

        List<Category> categories = categoryRepository.findAll();

        return Collections.singletonMap("categories", categories.stream()
                .map(category -> CategoryDto.builder()
                        .category(category)
                        .build())
                .collect(Collectors.toList()));
    }

}
