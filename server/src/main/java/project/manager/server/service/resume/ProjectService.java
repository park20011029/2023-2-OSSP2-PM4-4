package project.manager.server.service.resume;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.resume.Project;
import project.manager.server.dto.reponse.resume.ProjectDto;
import project.manager.server.dto.request.resume.ProjectRequestDto;
import project.manager.server.repository.resume.ProjectRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project createProject(ProjectRequestDto projectRequestDto) {

        Project newProject =
                Project.builder()
                        .projectName(projectRequestDto.getProjectName())
                        .description(projectRequestDto.getDescription())
                        .gitUrl(projectRequestDto.getGitUrl())
                        .build();

        return projectRepository.save(newProject);
    }

    public List<ProjectDto> readProject(List<Project> projects) {

        List<ProjectDto> projectDtos = new ArrayList<>();

        for (Project project : projects) {
            projectDtos.add(ProjectDto.builder().project(project).build());
        }

        return projectDtos;
    }
}
