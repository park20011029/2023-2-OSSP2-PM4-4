package project.manager.server.domain.post.building;

import jakarta.persistence.*;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PART")
@DynamicUpdate
public class Part {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_name",nullable = false)
    private String partName;

    @Column(name = "max_num", nullable = false)
    private Integer maxNum;

    @Column(name = "current_num")
    private Integer remainingApplicant;

    //---------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "buildingpost_id")
    private ApplyTechType applyTechType;

    @OneToMany(mappedBy = "part")
    private List<Apply> applyList;

    //---------------------------------------------------------

    @Builder
    public Part(String partName, Integer maxNum, ApplyTechType applyTechType) {
        this.partName = partName;
        this.maxNum = maxNum;
        this.remainingApplicant = maxNum;
        this.applyTechType = applyTechType;
    }
}
