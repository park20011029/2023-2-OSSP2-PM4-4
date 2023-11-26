package project.manager.server.service.post.building;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.building.ApplyTechType;
import project.manager.server.domain.post.building.Part;
import project.manager.server.dto.request.post.building.PartRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.post.building.ApplyTechTypeRepository;
import project.manager.server.repository.post.building.PartRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final ApplyTechTypeRepository applyTechTypeRepository;

    public Boolean createPart(Long applyTechTypeId, PartRequestDto partRequestDto) {
        ApplyTechType applyTechType = applyTechTypeRepository.findById(applyTechTypeId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Part newPart = Part.builder()
                .partName(partRequestDto.getPartName())
                .maxNum(partRequestDto.getMaxNum())
                .applyTechType(applyTechType)
                .build();

        partRepository.save(newPart);

        return true;
    }
}
