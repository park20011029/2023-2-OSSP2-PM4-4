package project.manager.server.service.resume;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.resume.Resume;
import project.manager.server.domain.resume.School;
import project.manager.server.dto.reponse.resume.SchoolDto;
import project.manager.server.dto.request.resume.SchoolRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.resume.SchoolRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public School createSchool(SchoolRequestDto schoolRequestDto, Resume resume) {

        School newSchool =
                School.builder()
                        .resume(resume)
                        .schoolRegister(schoolRequestDto.getSchoolRegister())
                        .name(schoolRequestDto.getName())
                        .build();

        return schoolRepository.save(newSchool);
    }

    public SchoolDto readSchool(Long resumeId) {
        School school = schoolRepository.findByResumeId(resumeId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        return SchoolDto.builder().school(school).build();
    }

    public void updateSchool(SchoolRequestDto schoolRequestDto) {
        School school = schoolRepository.findById(schoolRequestDto.getId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        school.updateSchool(schoolRequestDto.getName(), schoolRequestDto.getSchoolRegister());
    }

    public boolean deleteSchool(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        schoolRepository.delete(school);

        return true;
    }
}
