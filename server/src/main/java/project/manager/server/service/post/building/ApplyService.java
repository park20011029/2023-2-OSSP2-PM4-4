package project.manager.server.service.post.building;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.Part;
import project.manager.server.dto.request.post.building.ApplyRequestDto;
import project.manager.server.enums.PartState;
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

    public Boolean permitApply(Long writerId, Long applyId, ApplyRequestDto applyRequestDto) { //writerId == @LoginUser
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Part part = partRepository.findByIdWithPostAndUser(applyRequestDto.getPartId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        if (!part.getBuildingPost().getWriter().getId().equals(writerId)) { //팀 빌딩 게시글 작성자인지 확인
            throw new ApiException(ErrorDefine.ACCESS_DENIED);
        } if (part.addApplicant()) {
            return false;
        }
        apply.updateApply(PartState.APPROVAL);

        return true;
    }

    public Boolean denyApply(Long writerId, Long applyId, ApplyRequestDto applyRequestDto) { //writer == @LoginUser
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        Part part = partRepository.findByIdWithPostAndUser(applyRequestDto.getPartId())
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        if (!part.getBuildingPost().getWriter().getId().equals(writerId)) { //팀 빌딩 게시글 작성자인지 확인
            throw new ApiException(ErrorDefine.ACCESS_DENIED);
        }
        apply.updateApply(PartState.REFUSAL);

        return true;
    }

    public Boolean deleteApply(Long applyId, Long userId) { //userId == @LoginUser
        Apply apply = applyRepository.findByIdWithApplicant(applyId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        if (!apply.getApplicant().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.ACCESS_DENIED);
        }
        applyRepository.delete(apply);

        return true;
    }

}
