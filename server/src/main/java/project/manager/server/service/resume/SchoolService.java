package project.manager.server.service.resume;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.domain.Image;
import project.manager.server.domain.User;
import project.manager.server.domain.resume.School;
import project.manager.server.dto.request.resume.update.SchoolUpdateDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ImageRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.resume.SchoolRepository;
import project.manager.server.util.S3UploadUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;
    public Boolean updateSchool(SchoolUpdateDto schoolUpdateDto, MultipartFile file) {
        School school = schoolRepository.findById(schoolUpdateDto.getId())
                .orElseThrow(() ->
                        new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        String url = s3UploadUtil.upload(file, "pm4/");

        User user = userRepository.findById(schoolUpdateDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Image updateImage = Image.builder()
                .url(url)
                .uploader(user)
                .build();
        imageRepository.save(updateImage);

        imageRepository.delete(school.getSchoolImage());
        school.updateImage(updateImage);
        school.updateSchool(
                schoolUpdateDto.getName(),
                schoolUpdateDto.getMajor(),
                schoolUpdateDto.getSchoolRegister());

        return true;
    }
}
