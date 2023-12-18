package project.manager.server.service.post.contest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.manager.server.domain.Image;
import project.manager.server.domain.User;
import project.manager.server.domain.post.contest.*;
import project.manager.server.dto.reponse.post.PageInfo;
import project.manager.server.dto.reponse.post.contest.ContestPostDto;
import project.manager.server.dto.reponse.post.contest.ContestTitleDto;
import project.manager.server.dto.request.post.contest.ContestPostRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ImageRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.post.contest.*;
import project.manager.server.util.S3UploadUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class ContestPostService {

    private final BenefitRepository benefitRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizationRepository organizationRepository;
    private final ScaleRepository scaleRepository;
    private final TargetRepository targetRepository;
    private final UserRepository userRepository;
    private final ContestPostRepository contestPostRepository;
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;

    public Boolean createContestPost(ContestPostRequestDto contestPostRequestDto, MultipartFile file) {
        Benefit benefit = benefitRepository.findById(contestPostRequestDto.getBenefitId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Category category = categoryRepository.findById(contestPostRequestDto.getCategoryId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Organization organization = organizationRepository.findById(contestPostRequestDto.getOrganizationId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Scale scale = scaleRepository.findById(contestPostRequestDto.getScaleId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Target target = targetRepository.findById(contestPostRequestDto.getTargetId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        User user = userRepository.findById(contestPostRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        String url = s3UploadUtil.upload(file, "pm4/");
        Image newImage = Image.builder()
                .url(url)
                .uploader(user)
                .build();
        imageRepository.save(newImage);

        ContestPost newContestPost = ContestPost.builder()
                .contestImage(newImage)
                .contestPostRequestDto(contestPostRequestDto)
                .writer(user)
                .benefit(benefit)
                .category(category)
                .organization(organization)
                .scale(scale)
                .target(target)
                .build();

        contestPostRepository.save(newContestPost);

        return true;
    }

    public Map<String, Object> readContestList(Integer page, Integer size) {
        Page<ContestPost> contestPosts = contestPostRepository.findAllWithUser(LocalDate.now(), PageRequest.of(page, size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(contestPosts.getNumber() + 1)
                .totalPages(contestPosts.getTotalPages())
                .pageSize(contestPosts.getSize())
                .currentItems(contestPosts.getNumberOfElements())
                .totalItems(contestPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();

        result.put("contestPosts", contestPosts.getContent().stream()
                .map(post -> ContestTitleDto.builder()
                        .userId(post.getWriter().getId())
                        .user(post.getWriter().getName())
                        .imageUrl(post.getContestImage().getUrl())
                        .contestId(post.getId())
                        .title(post.getTitle())
                        .startAt(post.getStartAt())
                        .endAt(post.getEndAt())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public ContestPostDto readContestPost(Long contestPostId) {
        ContestPost contestPost = contestPostRepository.findById(contestPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        return ContestPostDto.builder()
                .contestPost(contestPost)
                .build();
    }

    public Map<String, Object> findContestPostList(
            String keyWord,
            Integer page,
            Integer size,
            List<Long> scales,
            List<Long> categories,
            List<Long> organizations,
            List<Long> targets,
            List<Long> benefits) {

        Page<ContestPost> contestPosts = contestPostRepository.searchContestPostList(
                scales,
                categories,
                organizations,
                targets,
                benefits,
                keyWord,
                LocalDate.now(),
                PageRequest.of(page, size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(contestPosts.getNumber() + 1)
                .totalPages(contestPosts.getTotalPages())
                .pageSize(contestPosts.getSize())
                .currentItems(contestPosts.getNumberOfElements())
                .totalItems(contestPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("contestPosts", contestPosts.stream()
                .map(post -> ContestTitleDto.builder()
                        .imageUrl(post.getContestImage().getUrl())
                        .userId(post.getWriter().getId())
                        .user(post.getWriter().getName())
                        .contestId(post.getId())
                        .title(post.getTitle())
                        .startAt(post.getStartAt())
                        .endAt(post.getEndAt())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Map<String, Object> readMyContestList(Long userId, Integer page, Integer size) {
        Page<ContestPost> contestPosts = contestPostRepository.findByWriter(LocalDate.now(), userId, PageRequest.of(page, size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(contestPosts.getNumber() + 1)
                .totalPages(contestPosts.getTotalPages())
                .pageSize(contestPosts.getSize())
                .currentItems(contestPosts.getNumberOfElements())
                .totalItems(contestPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("myContestPosts", contestPosts.stream()
                .map(post -> ContestTitleDto.builder()
                        .imageUrl(post.getContestImage().getUrl())
                        .userId(post.getWriter().getId())
                        .user(post.getWriter().getName())
                        .contestId(post.getId())
                        .title(post.getTitle())
                        .startAt(post.getStartAt())
                        .endAt(post.getEndAt())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Boolean updateContestPost(Long contestPostId, ContestPostRequestDto contestPostRequestDto, MultipartFile file) {
        ContestPost contestPost = contestPostRepository.findById(contestPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Benefit benefit = benefitRepository.findById(contestPostRequestDto.getBenefitId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Category category = categoryRepository.findById(contestPostRequestDto.getCategoryId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Organization organization = organizationRepository.findById(contestPostRequestDto.getOrganizationId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Scale scale = scaleRepository.findById(contestPostRequestDto.getScaleId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Target target = targetRepository.findById(contestPostRequestDto.getTargetId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        String url = s3UploadUtil.upload(file, "pm4/");
        Image newImage = Image.builder()
                .url(url)
                .uploader(contestPost.getWriter())
                .build();
        imageRepository.save(newImage);

        contestPost.updateContestPost(contestPostRequestDto, category, scale, benefit, target, organization, newImage);

        return true;
    }

}
