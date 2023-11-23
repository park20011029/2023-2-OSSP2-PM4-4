package project.manager.server.domain.post.contest;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BENEFIT_TB")
@DynamicUpdate
public class Benefit {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "benefit")
    private String benefit;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ContestPost> contestPosts = new ArrayList<>();

    // ------------------------------------------------------------
    @Builder
    public Benefit(String benefit){
        this.benefit = benefit;
    }
}
