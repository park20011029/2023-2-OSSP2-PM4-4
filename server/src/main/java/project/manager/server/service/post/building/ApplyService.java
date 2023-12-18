package project.manager.server.service.post.building;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.User;
import project.manager.server.domain.post.building.Apply;
import project.manager.server.domain.post.building.Part;
import project.manager.server.dto.reponse.post.PageInfo;
import project.manager.server.dto.reponse.post.building.ApplyDto;
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

    public Map<String, Object> readMyApplyList(Long userId, Integer page, Integer size) {
        Page<Apply> applies = applyRepository.findByUserIdWithPartAndPost(userId, PageRequest.of(page, size));

        PageInfo pageInfo = PageInfo.builder()
                .currentPage(applies.getNumber() + 1)
                .totalPages(applies.getTotalPages())
                .pageSize(applies.getSize())
                .currentItems(applies.getNumberOfElements())
                .totalItems(applies.getTotalElements())
                .build();

        Map<String, Object> result = new HashMap<>();
        result.put("applyList", applies.stream()
                .map(apply -> ApplyDto.builder()
                        .applyId(apply.getId())
                        .buildingPostId(apply.getPart().getBuildingPost().getId())
                        .buildingPost(apply.getPart().getBuildingPost().getTitle())
                        .partName(apply.getPart().getPartName())
                        .state(apply.getState().getToKorean())
                        .build())
                .collect(Collectors.toList()));

        result.put("pageInfo", pageInfo);

        return result;
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

    public Boolean denyApply(Long applyId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        apply.updateApply(PartState.REFUSAL);

        return true;
    }

    public Map<String, Object> applyListForLeader(Long buildingPostId) {
        List<Apply> applies = applyRepository.findByBuildingPostId(buildingPostId);

        Map<String, Object> result = new HashMap<>();
        result.put("applyList", applies.stream()
                .map(apply -> ApplyDto.builder()
                        .applyId(apply.getId())
                        .userId(apply.getApplicant().getId())
                        .nickName(apply.getApplicant().getNickName())
                        .partId(apply.getPart().getId())
                        .partName(apply.getPart().getPartName())
                        .state(apply.getState().getToKorean())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public Map<String, Object> applyListInBuildingPost(Long buildingPostId) {
        List<Apply> applies = applyRepository.findByBuildingPostIdWithState(buildingPostId);

        Map<String, Object> result = new HashMap<>();
        result.put("applyList", applies.stream()
                .map(apply -> ApplyDto.builder()
                        .userId(apply.getApplicant().getId())
                        .nickName(apply.getApplicant().getNickName())
                        .partName(apply.getPart().getPartName())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public Boolean deleteApply(Long applyId, Long userId) { //userId == @LoginUser
        Apply apply = applyRepository.findByIdWithApplicant(applyId)
                .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));

        if (!apply.getApplicant().getId().equals(userId)) {
            throw new ApiException(ErrorDefine.ACCESS_DENIED);
        }

        if (apply.getState().equals(PartState.APPROVAL)) {
            throw new ApiException(ErrorDefine.ACCESS_DENIED);
        }
        applyRepository.delete(apply);

        return true;
    }

}
