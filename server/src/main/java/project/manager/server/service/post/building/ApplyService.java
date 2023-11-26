package project.manager.server.service.post.building;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.Part;
import project.manager.server.dto.request.post.building.ApplyRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.post.building.ApplyRepository;
import project.manager.server.repository.post.building.PartRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final UserRepository userRepository;
    private final PartRepository partRepository;
    private final ApplyRepository applyRepository;

    public Boolean createApply(ApplyRequestDto applyRequestDto) {
        User applicant = userRepository.findById(applyRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        Part part = partRepository.findById(applyRequestDto.getPartId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        Apply newApply = Apply.builder()
                .applicant(applicant)
                .part(part)
                .build();

        applyRepository.save(newApply);

        return true;
    }
}
