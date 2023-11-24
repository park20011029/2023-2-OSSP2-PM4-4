package project.manager.server.domain.resume;

import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "AWARD_TB")
@DynamicUpdate
public class Award {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "competition", nullable = false)
    private String competition;

    @Column(name = "award_year", nullable = false)
    private LocalDate awardYear;

    @Column(name = "award_type", nullable = false)
    private String awardType;

    // -------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // -------------------------------------------------------------------

    @Builder
    public Award(String competition, LocalDate awardYear, String awardType, Resume resume) {
        this.resume = resume;
        this.competition = competition;
        this.awardYear = awardYear;
        this.awardType = awardType;
    }

    public void updateAward(String competition, LocalDate awardYear, String awardType) {
        this.competition = competition;
        this.awardYear = awardYear;
        this.awardType = awardType;
    }
}
