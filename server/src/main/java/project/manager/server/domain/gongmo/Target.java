package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.gongmo.TargetType;

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
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Builder
    public Target(Target target) {
        this.targetType = target.getTargetType();
    }

}
