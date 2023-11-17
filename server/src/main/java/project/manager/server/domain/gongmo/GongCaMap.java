package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "GONGCAMAP")
@DynamicUpdate
public class GongCaMap {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catebory_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scale_id", nullable = false)
    private Scale scale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id", nullable = false)
    private Benefit benefit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private Target target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    // --------------------------------------------------------------

    @OneToOne
    @JoinColumn(name = "gongmo_id")
    private GongMoPost gongMoPost;

    @Builder
    public GongCaMap(
            GongMoPost gongMoPost,
            Category category,
            Scale scale,
            Benefit benefit,
            Target target,
            Organization organization) {
        this.gongMoPost = gongMoPost;
        this.category = category;
        this.scale = scale;
        this.benefit = benefit;
        this.target = target;
        this.organization = organization;
    }


   }
