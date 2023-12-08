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
@Table(name = "ORGANIZATION_TB")
@DynamicUpdate
public class Organization {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization")
    private String organization;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<ContestPost> contestPosts = new ArrayList<>();

    @Builder
    public Organization(String organization){
        this.organization = organization;
    }
}
