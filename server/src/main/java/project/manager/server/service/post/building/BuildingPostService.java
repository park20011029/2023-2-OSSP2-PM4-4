package project.manager.server.service.post.building;

import static project.manager.server.enums.Constant.BUILDING_POST_POINT;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.Review;
import project.manager.server.domain.User;
import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.building.Part;
import project.manager.server.domain.post.contest.ContestPost;
import project.manager.server.dto.reponse.post.PageInfo;
import project.manager.server.dto.reponse.post.building.BuildingPostDto;
import project.manager.server.dto.reponse.post.building.BuildingTitleDto;
import project.manager.server.dto.reponse.post.building.PartDto;
import project.manager.server.dto.request.post.building.BuildingPostRequestDto;
import project.manager.server.dto.request.post.building.BuildingPostUpdateDto;
import project.manager.server.enums.Constant;
import project.manager.server.enums.PartState;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.ReviewRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.post.building.ApplyRepository;
import project.manager.server.repository.post.building.BuildingPostRepository;
import project.manager.server.repository.post.building.PartRepository;
import project.manager.server.repository.post.contest.ContestPostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingPostService {

    private final BuildingPostRepository buildingPostRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final ContestPostRepository contestPostRepository;
    private final ReviewRepository reviewRepository;
    private final ApplyRepository applyRepository;

    public Boolean createBuildingPost(Long contestPostId , BuildingPostRequestDto buildingPostRequestDto) {
        User writer = userRepository.findById(buildingPostRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        ContestPost contestPost = contestPostRepository
                .findById(contestPostId).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        boolean flag =false;

        if (buildingPostRequestDto.isUsePoint()) {
            if (writer.getPoint() < BUILDING_POST_POINT) {
                throw new RuntimeException("포인트도 없는게 돌아가라");
            }
            flag = true;
        }

        BuildingPost newBuildingPost = BuildingPost.builder()
                .buildingPostRequestDto(buildingPostRequestDto)
                .writer(writer)
                .contestPost(contestPost)
                .build();
        buildingPostRepository.save(newBuildingPost);

        if (flag) {
            newBuildingPost.upperPost();
            writer.updatePoint(writer.getPoint() - BUILDING_POST_POINT);
        }

        buildingPostRequestDto.getPartList().forEach(partRequestDto ->
                partRepository.save(Part.builder()
                        .partName(partRequestDto.getPartName())
                        .techType(partRequestDto.getTechType())
                        .maxApplicant(partRequestDto.getMaxApplicant())
                        .buildingPost(newBuildingPost)
                        .build()));

        return true;
    }

    public Map<String, Object> readBuildingPostList(Long contestPostId, Integer page, Integer size) {
        ContestPost contestPost = contestPostRepository.findById(contestPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Page<BuildingPost> buildingPosts = buildingPostRepository
                .findByContestPostWithUser(
                        contestPost,
                        PageRequest.of(page, size,
                                Sort.by(Sort.Order.asc("isRecruiting"),
                                        Sort.Order.desc("upperDate"),
                                        Sort.Order.desc("createAt"))));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(buildingPosts.getNumber() + 1)
                .totalPages(buildingPosts.getTotalPages())
                .pageSize(buildingPosts.getSize())
                .currentItems(buildingPosts.getNumberOfElements())
                .totalItems(buildingPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("buildingPosts", buildingPosts.stream()
                .map(post -> BuildingTitleDto.builder()
                        .user(post.getWriter().getName())
                        .userId(post.getWriter().getId())
                        .title(post.getTitle())
                        .postId(post.getId())
                        .creatAt(post.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Map<String, Object> readBuildingPost(Long buildingPostId) {
        BuildingPost buildingPost = buildingPostRepository.findByIdAndIsDeleteFalse(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Map<String, Object> result = new HashMap<>();

        result.put("buildingPost", BuildingPostDto.builder()
                .title(buildingPost.getTitle())
                .content(buildingPost.getContent())
                .userId(buildingPost.getWriter().getId())
                .user(buildingPost.getWriter().getName())
                .creatAt(buildingPost.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build());

        List<Part> partList = partRepository.findByBuildingPost(buildingPost);
        for (Part part : partList) {
            // 키값 중복 체크해서
            if (result.containsKey(part.getTechType().getLabel())) {
                List<PartDto> partDtos = (List<PartDto>)result.get(part.getTechType().getLabel());

                partDtos.add(PartDto.builder()
                        .partId(part.getId())
                        .partName(part.getPartName())
                        .currentApplicant(part.getCurrentApplicant())
                        .maxApplicant(part.getMaxApplicant())
                        .build());
                result.put(part.getTechType().getLabel(), partDtos);
            } else {
                List<PartDto> partDtos = new ArrayList<>();

                partDtos.add(PartDto.builder()
                        .partId(part.getId())
                        .partName(part.getPartName())
                        .currentApplicant(part.getCurrentApplicant())
                        .maxApplicant(part.getMaxApplicant())
                        .build());
                result.put(part.getTechType().getLabel(), partDtos);
            }
        }

        return result;
    }

    public Map<String, Object> readMyBuildingList(Long userId, Integer page, Integer size) {

        Page<BuildingPost> buildingPosts = buildingPostRepository.findBuildingPostByWithUser(
                userId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt")));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(buildingPosts.getNumber() + 1)
                .totalPages(buildingPosts.getTotalPages())
                .pageSize(buildingPosts.getSize())
                .currentItems(buildingPosts.getNumberOfElements())
                .totalItems(buildingPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("myBuildingPosts", buildingPosts.stream()
                .map(post -> BuildingTitleDto.builder()
                        .user(post.getWriter().getName())
                        .userId(post.getWriter().getId())
                        .title(post.getTitle())
                        .postId(post.getId())
                        .creatAt(post.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Boolean updateBuildingPost(Long buildingPostId, BuildingPostUpdateDto updateDto) {
        BuildingPost buildingPost = buildingPostRepository.findByIdAndRecruitingIsTrue(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        if (updateDto.isUsePoint()) {
            if (buildingPost.getWriter().getPoint() < BUILDING_POST_POINT) {
                throw new RuntimeException("포인트도 없는게 돌아가라");
            } if (!buildingPost.getUpperDate().equals(Constant.MYSQL_MIN_DATE)) {
                throw new RuntimeException("수정 못함");
            }
            buildingPost.upperPost();
            buildingPost.getWriter().updatePoint(buildingPost.getWriter().getPoint() - BUILDING_POST_POINT);
        }
        buildingPost.updateBuildingPost(updateDto.getTitle(),updateDto.getContent());

        return true;
    }

    public Boolean endBuildingPost(Long buildingPostId) {

        BuildingPost buildingPost = buildingPostRepository.findByIdAndRecruitingIsTrue(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        List<Apply> applyList = applyRepository.findByPostIdWithPartAndApply(buildingPostId);
        applyList.forEach(apply -> {
            if (apply.getState().equals(PartState.STANDBY)) {
                throw new RuntimeException("One or more applys are in STANDBY state.");
            }
        });

        buildingPost.buildingPostClose();

        List<Apply> sortApplyList = applyList.stream()
                .sorted(Comparator.comparing(apply -> apply.getApplicant().getId()))
                .toList();

        AtomicLong past = new AtomicLong(0L);
        sortApplyList.forEach(applyFirst -> {
            for (Apply apply: sortApplyList) {
                if (!applyFirst.getApplicant().getId().equals(past.get()) && !applyFirst.getApplicant().getId().equals(apply.getApplicant().getId())) {
                    reviewRepository.save(Review.builder()
                            .partName(apply.getPart().getPartName())
                            .buildingPost(buildingPost)
                            .reviewer(applyFirst.getApplicant())
                            .reviewee(apply.getApplicant())
                            .build());
                }
            }
            past.set(applyFirst.getApplicant().getId());
        });

        return true;
    }

    // 지원 승인 대기중인 사람 있는지 확인, 지원 승인된 사람 있는지 확인
    public Boolean deleteBuildingPost(Long buildingPostId) {
        BuildingPost buildingPost = buildingPostRepository.findByIdAndRecruitingIsTrue(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        List<Apply> applyList = applyRepository.findByPostIdWithPartAndApply(buildingPostId);
        applyList.forEach(apply -> {
            if (!apply.getState().equals(PartState.REFUSAL)) {
                throw new RuntimeException("One or more applys are is not REFUSAL state.");
            }
        });

        buildingPost.deleteBuildingPost();

        return false;
    }

}
