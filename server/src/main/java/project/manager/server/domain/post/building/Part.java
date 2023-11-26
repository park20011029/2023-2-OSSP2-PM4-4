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
@Table(name = "PART_TB")
@DynamicUpdate
public class Part {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_name",nullable = false)
    private String partName;

    @Column(name = "max_applicant", nullable = false)
    private Integer maxApplicant;

    @Column(name = "current_applicant")
    private Integer currentApplicant;

    @Column(name = "tech_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TechType techType;

    //---------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingpost_id",nullable = false)
    private BuildingPost buildingPost;

    @OneToMany(mappedBy = "part")
    private List<Apply> applyList;

    //---------------------------------------------------------

    @Builder
    public Part(String partName, Integer maxApplicant, BuildingPost buildingPost, TechType techType) {
        this.partName = partName;
        this.maxApplicant = maxApplicant;
        this.currentApplicant = 0;
        this.techType = techType;
        this.buildingPost = buildingPost;
    }

    // 파트 모집 최대인원 변경
    public boolean fixMaxApplicant(Integer fixNum) {
        if (fixNum < this.currentApplicant) {
            return false;
        }
        this.maxApplicant = fixNum;

        return true;
    }

    public boolean addApplicant() {
        if (this.maxApplicant <= this.currentApplicant) {
            return false;
        }
        this.currentApplicant += 1;

        return true;
    }

    public void deleteApplicant() {
        this.currentApplicant -= 1;
    }

    // 업데이트 필요 없음
}
