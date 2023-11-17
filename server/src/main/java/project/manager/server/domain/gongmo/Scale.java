package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.gongmo.ScaleType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "SCALE_TB")
@DynamicUpdate
public class Scale {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scale")
    @Enumerated(EnumType.STRING)
    private ScaleType scaleType;

   @Builder
    public Scale(Scale scale){
       this.scaleType=scale.getScaleType();
   }


}
