package project.manager.server.service.resume;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.domain.Image;
import project.manager.server.domain.User;
import project.manager.server.domain.resume.Award;
import project.manager.server.domain.resume.Resume;
import project.manager.server.dto.request.resume.create.AwardRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ImageRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.resume.AwardRepository;
import project.manager.server.repository.resume.ResumeRepository;
import project.manager.server.util.S3UploadUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;

    public Long addAward(Long resumeId, AwardRequestDto awardRequestDto, MultipartFile file) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ApiException(ErrorDefine.RESUME_NOT_FOUND));

        User user = userRepository.findById(awardRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        String url = s3UploadUtil.upload(file, "pm4/");

        Image newImage = Image.builder()
                .url(url)
                .uploader(user)
                .build();
        imageRepository.save(newImage);

        Award newAward = Award.builder()
                .awardImage(newImage)
                .resume(resume)
                .awardYear(awardRequestDto.getAwardYear())
                .awardType(awardRequestDto.getAwardType())
                .competition(awardRequestDto.getCompetition())
                .build();
        awardRepository.save(newAward);

        return newAward.getId();
    }

    public boolean deleteAward(Long awardId) {
        Award award = awardRepository
                        .findById(awardId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        imageRepository.delete(award.getAwardImage());
        awardRepository.delete(award);

        return true;
    }
}
