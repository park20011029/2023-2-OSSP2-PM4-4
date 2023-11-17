package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.gongmo.CategoryType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CATEGORY")
@DynamicUpdate
public class Category {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Builder
    public Category(Category category){
        this.categoryType = category.getCategoryType();
    }

   }
