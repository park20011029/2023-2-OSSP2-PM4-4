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
@Table(name = "RESEUMEPROJECT_TB")
@DynamicUpdate
public class ResumeProject {
    @Id
    @Column(name = "id",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "description")
    private String projectDescription;

    @Column(name = "project_git")
    private String projectGit;

    @Builder
    public ResumeProject(Resume resume, String projectName,String projectGit, String projectDescription){
        this.resume = resume;
        this.projectName = projectName;
        this.projectGit = projectGit;
        this.projectDescription = projectDescription;
    }

    public void updateResumeProject(Resume resume, String projectName,String projectGit, String projectDescription){
        this.resume = resume;
        this.projectName = projectName;
        this.projectGit = projectGit;
        this.projectDescription = projectDescription;
    }
}
