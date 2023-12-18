package project.manager.server.service.post.building;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.BuildingPost;
import project.manager.server.domain.post.building.Part;
import project.manager.server.dto.request.post.building.PartRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.post.building.ApplyRepository;
import project.manager.server.repository.post.building.BuildingPostRepository;
import project.manager.server.repository.post.building.PartRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final ApplyRepository applyRepository;
    private final BuildingPostRepository buildingPostRepository;

    public Boolean createPart(Long buildingPostId, PartRequestDto partRequestDto) {
        BuildingPost buildingPost = buildingPostRepository.findByIdAndIsDeleteFalse(buildingPostId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Part newPart = Part.builder()
                .partName(partRequestDto.getPartName())
                .maxApplicant(partRequestDto.getMaxApplicant())
                .techType(partRequestDto.getTechType())
                .buildingPost(buildingPost)
                .build();

        partRepository.save(newPart);

        return true;
    }

    public Boolean fixMaxApplicant(Long partId, Integer fixNum) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        return part.fixMaxApplicant(fixNum);
    }

    public Boolean deletePart(Long partId) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        List<Apply> applies = applyRepository.findByPartIdCanDelete(partId);

        if(applies.isEmpty()) {
            partRepository.delete(part);
            return true;
        } else {
            return false;
        }
    }
}
