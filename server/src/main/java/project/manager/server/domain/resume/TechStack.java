package project.manager.server.domain.resume;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.TechType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TECHSTACK_TB")
@DynamicUpdate
public class TechStack {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tech_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TechType techType;

    @Column(name = "tech", nullable = false)
    private String tech;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    // -------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // -------------------------------------------------------------------

    @Builder
    public TechStack(TechType techType, String tech, String description) {
        this.tech = tech;
        this.techType = techType;
        this.description = description;
    }

    public void updateResumeTech(TechType techType, String tech, String techDescription) {
        this.tech = tech;
        this.techType = techType;
        this.description = techDescription;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
