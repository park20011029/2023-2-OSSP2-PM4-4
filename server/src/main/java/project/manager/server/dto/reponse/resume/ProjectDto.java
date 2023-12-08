package project.manager.server.dto.reponse.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.resume.Project;

@Getter
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    private String gitUrl;
    private String description;
    private String projectName;

    @Builder
    public ProjectDto(Project project) {
        this.id = project.getId();
        this.gitUrl = project.getGitUrl();
        this.description = project.getDescription();
        this.projectName = project.getProjectName();
    }
}
