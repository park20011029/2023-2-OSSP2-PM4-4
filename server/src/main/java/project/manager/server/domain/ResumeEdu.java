package project.manager.server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.ResumeEduState;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESEUMEEDU_TB")
@DynamicUpdate
public class ResumeEdu {
    @Id
    @Column(name = "id",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "school")
    private String school;

    @Column(name = "enroll")
    private Integer enroll;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ResumeEduState resumeEduState;

    @Builder
    public ResumeEdu(Resume resume, String school, Integer enroll, ResumeEduState resumeEduState){
        this.resume = resume;
        this.school = school;
        this.enroll = enroll;
        this.resumeEduState = resumeEduState;
    }

    public void updateResumeEdu(Resume resume, String school, Integer enroll, ResumeEduState resumeEduState){
        this.resume = resume;
        this.school = school;
        this.enroll = enroll;
        this.resumeEduState = resumeEduState;
    }

}
