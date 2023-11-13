package project.manager.server.domain.resume;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.SchoolRegister;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "SCHOOL_TB")
@DynamicUpdate
public class School {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "school_register", nullable = false)
    @Enumerated(EnumType.STRING)
    private SchoolRegister schoolRegister;

    // -------------------------------------------------------------------

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // -------------------------------------------------------------------

    @Builder
    public School(String name, SchoolRegister schoolRegister) {
        this.schoolRegister = schoolRegister;
        this.name = name;
    }

    public void updateSchool(String name, SchoolRegister schoolRegister) {
        this.schoolRegister = schoolRegister;
        this.name = name;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
