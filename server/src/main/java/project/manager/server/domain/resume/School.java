package project.manager.server.domain.resume;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.Image;
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

    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "school_register", nullable = false)
    @Enumerated(EnumType.STRING)
    private SchoolRegister schoolRegister;

    // -------------------------------------------------------------------

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schoolimage_id")
    private Image schoolImage;

    // -------------------------------------------------------------------

    @Builder
    public School(String name, SchoolRegister schoolRegister, Resume resume, String major, Image schoolImage) {
        this.resume = resume;
        this.major = major;
        this.schoolImage = schoolImage;
        this.schoolRegister = schoolRegister;
        this.name = name;
    }

    public void updateSchool(String name, String major, SchoolRegister schoolRegister) {
        this.schoolRegister = schoolRegister;
        this.name = name;
        this.major = major;
    }

    public void updateImage(Image image) {
        this.schoolImage = image;
    }

}
