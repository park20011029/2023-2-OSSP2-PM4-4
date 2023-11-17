package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.gongmo.BenefitType;

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
    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;

    // ------------------------------------------------------------
    @Builder
    public Benefit(Benefit benefit){
        this.benefitType = benefit.getBenefitType();
    }
}
