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
@Table(name = "TARGET_TB")
@DynamicUpdate
public class Target {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target")
    private String target;

    @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
    private List<ContestPost> contestPosts = new ArrayList<>();

    @Builder
    public Target(String target) {
        this.target = target;
    }

}
