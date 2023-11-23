package project.manager.server.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.post.contest.Category;
import project.manager.server.domain.post.contest.Organization;
import project.manager.server.dto.reponse.post.contest.CategoryDto;
import project.manager.server.dto.reponse.post.contest.TargetDto;
import project.manager.server.dto.request.post.contest.CategoryRequestDto;
import project.manager.server.repository.post.contast.CategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

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

