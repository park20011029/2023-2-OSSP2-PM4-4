package project.manager.server.service.post.building;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.building.ApplyTechType;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.dto.request.post.building.ApplyTechTypeRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.post.building.ApplyTechTypeRepository;
import project.manager.server.repository.post.building.BuildingPostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyTechTypeService {

    private final ApplyTechTypeRepository applyTechTypeRepository;
    private final BuildingPostRepository buildingPostRepository;

    public Boolean createApplyTechType(Long buildingPostId, ApplyTechTypeRequestDto applyTechTypeRequestDto) {
        BuildingPost buildingPost = buildingPostRepository
                .findById(buildingPostId).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        ApplyTechType newApplyTechType = ApplyTechType.builder()
                .techType(applyTechTypeRequestDto.getTechType())
                .buildingPost(buildingPost)
                .build();

        applyTechTypeRepository.save(newApplyTechType);

        return true;
    }
}
