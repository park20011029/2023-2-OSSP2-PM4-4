package project.manager.server.service.resume;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.resume.Project;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.reponse.resume.ProjectDto;
import project.manager.server.dto.request.resume.ProjectRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.ProjectRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project createProject(ProjectRequestDto projectRequestDto, Resume resume) {

        Project newProject = Project.builder()
                        .resume(resume)
                        .projectName(projectRequestDto.getProjectName())
                        .description(projectRequestDto.getDescription())
                        .gitUrl(projectRequestDto.getGitUrl())
                        .build();

        return projectRepository.save(newProject);
    }

    public List<ProjectDto> readProject(List<Project> projects) {
        if (projects == null) return null;

        return projects.stream()
                .map(project -> ProjectDto.builder().project(project).build())
                .collect(Collectors.toList());
    }

    public void updateProject(ProjectRequestDto projectRequestDto) {
        Project project = projectRepository.findById(projectRequestDto.getId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        project.updateProject(
                projectRequestDto.getProjectName(),
                projectRequestDto.getGitUrl(),
                projectRequestDto.getDescription());
    }

    public boolean deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        projectRepository.delete(project);

        return true;
    }
}
