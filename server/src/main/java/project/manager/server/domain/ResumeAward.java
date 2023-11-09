package project.manager.server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESEUMEAWARD_TB")
@DynamicUpdate
public class ResumeAward {
    @Id
    @Column(name = "id",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ColumnDefault("a")
    @Column(name = "compitition")
    private String congress;

    @Column(name = "award_year")
    private Integer awardYear;

    @Column(name = "award_type")
    private String awardType;

    @Builder
    public ResumeAward(Resume resume, String congress, Integer awardYear, String awardType){
        this.resume = resume;
        this.congress = congress;
        this.awardYear = awardYear;
        this.awardType = awardType;
    }

    public void updateResumeAward(Resume resume, String congress, Integer awardYear, String awardType){
        this.resume = resume;
        this.congress = congress;
        this.awardYear = awardYear;
        this.awardType = awardType;
    }


}
