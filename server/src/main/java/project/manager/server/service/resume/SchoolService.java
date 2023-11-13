package project.manager.server.service.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public School createSchool(SchoolRequestDto schoolRequestDto) {

        School newSchool =
                School.builder()
                        .schoolRegister(schoolRequestDto.getSchoolRegister())
                        .name(schoolRequestDto.getName())
                        .build();

        return schoolRepository.save(newSchool);
    }

    public SchoolDto readSchool(Long resumeId) {
        School school = schoolRepository.findByResumeId(resumeId).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        return SchoolDto.builder().school(school).build();
    }
}
