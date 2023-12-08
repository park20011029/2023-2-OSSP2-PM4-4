package project.manager.server.domain.post.contest;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

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

    @Column(name = "category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ContestPost> contestPosts = new ArrayList<>();

    @Builder
    public Category(String category){
        this.category = category;
    }

   }
