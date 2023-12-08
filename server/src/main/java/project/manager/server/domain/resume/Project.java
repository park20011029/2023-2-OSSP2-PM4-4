package project.manager.server.domain.resume;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PROJECT_TB")
@DynamicUpdate
public class Project {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "project_git_url", nullable = false)
    private String gitUrl;

    // -------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // -------------------------------------------------------------------

    @Builder
    public Project(String projectName, String gitUrl, String description, Resume resume) {
        this.resume = resume;
        this.projectName = projectName;
        this.gitUrl = gitUrl;
        this.description = description;
    }

    public void updateProject(String projectName, String projectGit, String projectDescription) {
        this.projectName = projectName;
        this.gitUrl = projectGit;
        this.description = projectDescription;
    }

}
