package project.manager.server.service.post.building;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.contest.ContestPost;
import project.manager.server.dto.request.post.building.BuildingPostRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.post.building.ApplyRepository;
import project.manager.server.repository.post.building.ApplyTechTypeRepository;
import project.manager.server.repository.post.building.BuildingPostRepository;
import project.manager.server.repository.post.building.PartRepository;
import project.manager.server.repository.post.contast.ContestPostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingPostService {

    private final BuildingPostRepository buildingPostRepository;
    private final ApplyRepository applyRepository;
    private final PartRepository partRepository;
    private final ApplyTechTypeRepository applyTechTypeRepository;
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

        return true;
    }
}
