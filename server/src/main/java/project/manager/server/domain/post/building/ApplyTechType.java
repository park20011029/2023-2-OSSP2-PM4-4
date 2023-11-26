package project.manager.server.domain.post.building;

import jakarta.persistence.*;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.enums.TechType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "APPLY_TECHTYPE_TB")
@DynamicUpdate
public class ApplyTechType {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tech_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TechType techType;

    //---------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingpost_id",nullable = false)
    private BuildingPost buildingPost;

    @OneToMany(mappedBy = "applyTechType", fetch = FetchType.LAZY)
    private List<Part> parts;

    //---------------------------------------------------------

    @Builder
    public ApplyTechType(TechType techType, BuildingPost buildingPost) {
        this.techType = techType;
        this.buildingPost = buildingPost;
    }
}
