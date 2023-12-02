package project.manager.server.domain.post.building;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;
import project.manager.server.enums.PartState;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "APPLY_TB")
@DynamicUpdate
public class Apply {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PartState state;

    //---------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    //---------------------------------------------------------

    @Builder
    public Apply(User applicant, Part part) {
        this.state = PartState.STANDBY;
        this.applicant = applicant;
        this.part = part;
    }

    public void updateApply(PartState partState) {
        this.state = partState;
    }
}
