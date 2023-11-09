package project.manager.server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.TechType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESEUMETECH_TB")
@DynamicUpdate
public class ResumeTech {
    @Id
    @Column(name = "id",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "tech_type")
    @Enumerated(EnumType.STRING)
    private TechType techType;

    @Column(name = "description")
    private String techDescription;

    @Builder
    public ResumeTech(Resume resume, TechType techType, String techDescription){
        this.resume = resume;
        this.techType = techType;
        this.techDescription = techDescription;
    }

    public void updateResumeTech(Resume resume, TechType techType, String techDescription){
        this.resume = resume;
        this.techType = techType;
        this.techDescription = techDescription;
    }
}
