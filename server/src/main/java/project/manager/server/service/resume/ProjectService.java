package project.manager.server.service.resume;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.resume.Project;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.request.resume.create.ProjectRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.ProjectRepository;
import project.manager.server.repository.resume.ResumeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;

    public Long addProject(Long resumeId, ProjectRequestDto projectRequestDto) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        Project newProject = Project.builder()
                .resume(resume)
                .projectName(projectRequestDto.getProjectName())
                .gitUrl(projectRequestDto.getGitUrl())
                .description(projectRequestDto.getDescription())
                .build();
        projectRepository.save(newProject);

        return newProject.getId();
    }


    public boolean deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        projectRepository.delete(project);

        return true;
    }
}
