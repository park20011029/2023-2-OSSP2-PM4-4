package project.manager.server.domain.resume;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;
import project.manager.server.domain.region.Gu;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESUME_TB")
@DynamicUpdate
public class Resume {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "gender", nullable = false)
    private boolean gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    // -------------------------------------------------------------------

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<TechStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<Award> awards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gu_id")
    private Gu gu;

    // -------------------------------------------------------------------

    @Builder
    public Resume(String job, boolean gender, LocalDate birth, User user, Gu gu) {
        this.gu = gu;
        this.user = user;
        this.job = job;
        this.gender = gender;
        this.birth = birth;
    }


    public void updateResume(String job, boolean gender, LocalDate birth, Gu gu) {
        this.gu = gu;
        this.job = job;
        this.gender = gender;
        this.birth = birth;
    }

}
