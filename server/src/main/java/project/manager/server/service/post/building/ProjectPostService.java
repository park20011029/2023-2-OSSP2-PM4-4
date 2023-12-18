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

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectPostService {

    private final BuildingPostRepository buildingPostRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ApplyRepository applyRepository;

    public Boolean createProjectPost(BuildingPostRequestDto projectPostRequestDto) {
        User writer = userRepository.findById(projectPostRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        boolean flag =false;

        if (projectPostRequestDto.isUsePoint()) {
            if (writer.getPoint() < BUILDING_POST_POINT) {
                throw new RuntimeException("포인트도 없는게 돌아가라");
            }
            flag = true;
        }

        BuildingPost newProjectPost = BuildingPost.builder()
                .buildingPostRequestDto(projectPostRequestDto)
                .writer(writer)
                .build();
        buildingPostRepository.save(newProjectPost);

        if (flag) {
            newProjectPost.upperPost();
            writer.updatePoint(writer.getPoint() - BUILDING_POST_POINT);
        }

        projectPostRequestDto.getPartList().forEach(partRequestDto ->
                partRepository.save(Part.builder()
                        .partName(partRequestDto.getPartName())
                        .techType(partRequestDto.getTechType())
                        .maxApplicant(partRequestDto.getMaxApplicant())
                        .buildingPost(newProjectPost)
                        .build()));

        return true;
    }

    public Map<String, Object> readProjectPostList(Integer page, Integer size) {
        Page<BuildingPost> projectPosts = buildingPostRepository
                .findProjectPostWithUser(
                        PageRequest.of(page, size,
                                Sort.by(Sort.Order.asc("isRecruiting"),
                                        Sort.Order.desc("upperDate"),
                                        Sort.Order.desc("createAt"))));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(projectPosts.getNumber() + 1)
                .totalPages(projectPosts.getTotalPages())
                .pageSize(projectPosts.getSize())
                .currentItems(projectPosts.getNumberOfElements())
                .totalItems(projectPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("projectPosts", projectPosts.stream()
                .map(post -> BuildingTitleDto.builder()
                        .user(post.getWriter().getNickName())
                        .userId(post.getWriter().getId())
                        .title(post.getTitle())
                        .postId(post.getId())
                        .creatAt(post.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Map<String, Object> searchProjectPost(String text, Integer page, Integer size) {
        Page<BuildingPost> projectPosts = buildingPostRepository
                .findProjectPostByText("%" + text + "%", PageRequest.of(page,size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(projectPosts.getNumber() + 1)
                .totalPages(projectPosts.getTotalPages())
                .pageSize(projectPosts.getSize())
                .currentItems(projectPosts.getNumberOfElements())
                .totalItems(projectPosts.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("projectPosts", projectPosts.stream()
                .map(post -> BuildingTitleDto.builder()
                        .user(post.getWriter().getNickName())
                        .userId(post.getWriter().getId())
                        .title(post.getTitle())
                        .postId(post.getId())
                        .creatAt(post.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Map<String, Object> readProjectPost(Long projectPostId) {
        BuildingPost projectPost = buildingPostRepository.findByIdAndIsDeleteFalse(projectPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Map<String, Object> result = new HashMap<>();

        result.put("projectPost", BuildingPostDto.builder()
                .title(projectPost.getTitle())
                .content(projectPost.getContent())
                .userId(projectPost.getWriter().getId())
                .user(projectPost.getWriter().getNickName())
                .creatAt(projectPost.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build());

        List<Part> partList = partRepository.findByBuildingPost(projectPost);
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

    public Map<String, Object> readMyProjectList(Long userId, Integer page, Integer size) {

        Page<BuildingPost> projectPost = buildingPostRepository.findProjectPostByWithUser(
                userId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt")));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(projectPost.getNumber() + 1)
                .totalPages(projectPost.getTotalPages())
                .pageSize(projectPost.getSize())
                .currentItems(projectPost.getNumberOfElements())
                .totalItems(projectPost.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("myProjectPosts", projectPost.stream()
                .map(post -> BuildingTitleDto.builder()
                        .user(post.getWriter().getNickName())
                        .userId(post.getWriter().getId())
                        .title(post.getTitle())
                        .postId(post.getId())
                        .creatAt(post.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
    }

    public Boolean updateProjectPost(Long projectPostId, BuildingPostUpdateDto updateDto) {
        BuildingPost projectPost = buildingPostRepository.findProjectPostByIdAndRecruiting(projectPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        if (updateDto.isUsePoint()) {
            if (projectPost.getWriter().getPoint() < BUILDING_POST_POINT) {
                throw new RuntimeException("포인트도 없는게 돌아가라");
            } if (!projectPost.getUpperDate().equals(Constant.MYSQL_MIN_DATE)) {
                throw new RuntimeException("수정 못함");
            }
            projectPost.upperPost();
            projectPost.getWriter().updatePoint(projectPost.getWriter().getPoint() - BUILDING_POST_POINT);
        }
        projectPost.updateBuildingPost(updateDto.getTitle(),updateDto.getContent());

        return true;
    }

    public Boolean endProjectPost(Long projectPostId) {

        BuildingPost projectPost = buildingPostRepository.findProjectPostByIdAndRecruiting(projectPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        List<Apply> applyList = applyRepository.findByPostIdWithPartAndApply(projectPostId);
        applyList.forEach(apply -> {
            if (apply.getState().equals(PartState.STANDBY)) {
                throw new RuntimeException("One or more applys are in STANDBY state.");
            }
        });

        projectPost.buildingPostClose();

        List<Apply> sortApplyList = applyList.stream()
                .sorted(Comparator.comparing(apply -> apply.getApplicant().getId()))
                .toList();

        AtomicLong past = new AtomicLong(0L);
        sortApplyList.forEach(applyFirst -> {
            for (Apply apply: sortApplyList) {
                if (!applyFirst.getApplicant().getId().equals(past.get()) && !applyFirst.getApplicant().getId().equals(apply.getApplicant().getId())) {
                    reviewRepository.save(Review.builder()
                            .partName(apply.getPart().getPartName())
                            .buildingPost(projectPost)
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
    public Boolean deleteProjectPost(Long projectPostId) {
        BuildingPost projectPost = buildingPostRepository.findProjectPostByIdAndRecruiting(projectPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        List<Apply> applyList = applyRepository.findByPostIdWithPartAndApply(projectPostId);
        applyList.forEach(apply -> {
            if (!apply.getState().equals(PartState.REFUSAL)) {
                throw new RuntimeException("One or more applys are is not REFUSAL state.");
            }
        });

        projectPost.deleteBuildingPost();

        return false;
    }

}
