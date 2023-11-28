package project.manager.server.service.post.building;


import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.building.Part;
import project.manager.server.domain.post.contest.ContestPost;
import project.manager.server.dto.reponse.post.building.BuildingPostDto;
import project.manager.server.dto.reponse.post.building.BuildingTitleDto;
import project.manager.server.dto.reponse.post.building.PartDto;
import project.manager.server.dto.request.post.building.BuildingPostRequestDto;
import project.manager.server.dto.request.post.building.BuildingPostUpdateDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;
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

    public Boolean createBuildingPost(Long contestPostId , BuildingPostRequestDto buildingPostRequestDto) {
        User writer = userRepository.findById(buildingPostRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        ContestPost contestPost = contestPostRepository
                .findById(contestPostId).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        BuildingPost newBuildingPost = BuildingPost.builder()
                .buildingPostRequestDto(buildingPostRequestDto)
                .writer(writer)
                .contestPost(contestPost)
                .build();

        buildingPostRepository.save(newBuildingPost);

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

        Page<BuildingPost> buildingPosts = buildingPostRepository.findByContestPostWithUser(
                contestPost,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt")));

        return Collections.singletonMap("buildingPosts", buildingPosts.stream()
                .map(post -> BuildingTitleDto.builder()
                        .user(post.getWriter().getName())
                        .userId(post.getWriter().getId())
                        .title(post.getTitle())
                        .buildingId(post.getId())
                        .creatAt(post.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .collect(Collectors.toList()));
    }

    public Map<String, Object> readBuildingPost(Long buildingPostId) {
        BuildingPost buildingPost = buildingPostRepository.findByIdAndIsDeleteFalse(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Map<String, Object> result = new HashMap<>();

//        BuildingPostDto b = BuildingPostDto.builder()
//                .title(buildingPost.getTitle())
//                .content(buildingPost.getContent())
//                .userId(buildingPost.getWriter().getId())
//                .user(buildingPost.getWriter().getName())
//                .creatAt(buildingPost.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
//                .build();
//
//        System.out.println(b.getUser());
//        System.out.println(b.getCreatAt());
//        System.out.println(b.getTitle());
//        System.out.println(b.getContent());
//        System.out.println(b.getUserId());
//
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

//                System.out.println("Part ID: " + p.getPartId());
//                System.out.println("Part Name: " + p.getPartName());
//                System.out.println("Current Applicant: " + p.getCurrentApplicant());
//                System.out.println("Max Applicant: " + p.getMaxApplicant());

                partDtos.add(PartDto.builder()
                        .partId(part.getId())
                        .partName(part.getPartName())
                        .currentApplicant(part.getCurrentApplicant())
                        .maxApplicant(part.getMaxApplicant())
                        .build());
                result.put(part.getTechType().getLabel(), partDtos);
            } else {
                List<PartDto> partDtos = new ArrayList<>();

//                System.out.println("ELSE");
//                System.out.println("Part ID: " + p.getPartId());
//                System.out.println("Part Name: " + p.getPartName());
//                System.out.println("Current Applicant: " + p.getCurrentApplicant());
//                System.out.println("Max Applicant: " + p.getMaxApplicant());
                partDtos.add(PartDto.builder()
                        .partId(part.getId())
                        .partName(part.getPartName())
                        .currentApplicant(part.getCurrentApplicant())
                        .maxApplicant(part.getMaxApplicant())
                        .build());
                result.put(part.getTechType().getLabel(), partDtos);
            }
        }

//        result.put(partList.stream()
//                .collect(Collectors.groupingBy(Part::getTechType),
//                        Collectors.mapping(part -> PartDto.builder()
//                                .partId(part.getId())
//                                .partName(part.getPartName())
//                                .currentApplicant(part.getCurrentApplicant())
//                                .maxApplicant(part.getMaxApplicant())), Collectors.toList()));
        return result;
    }

    public Boolean updateBuildingPost(Long buildingPostId, BuildingPostUpdateDto updateDto) {
        BuildingPost buildingPost = buildingPostRepository.findById(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        buildingPost.updateBuildingPost(updateDto.getTitle(),updateDto.getContent());

        return true;
    }

    // 지원 승인 대기중인 사람 있는지 확인, 지원 승인된 사람 있는지 확인
//    public Boolean deleteBuildingPost(Long buildingPostId) {
//        BuildingPost buildingPost = buildingPostRepository.findById(buildingPostId)
//                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
//        List<ApplyTechType> applyTechTypeList = applyTechTypeRepository.findByBuildingPost(buildingPost);
//
//        if (applyTechTypeList.isEmpty()) {
//            buildingPost.deleteBuildingPost();
//            return true;
//        }
//
//        return false;
//    }

}
