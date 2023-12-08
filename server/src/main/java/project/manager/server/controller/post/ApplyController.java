package project.manager.server.controller.post;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.request.post.building.ApplyRequestDto;
import project.manager.server.service.post.building.ApplyService;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    final private ApplyService applyService;

    //지원내역 생성
    @PostMapping("")
    public ResponseDto<Boolean> creatApply(@Valid @RequestBody ApplyRequestDto applyRequestDto) {

        return new ResponseDto<>(applyService.createApply(applyRequestDto));
    }

    //팀 빌딩 팀장이 지원 승인
    @PutMapping("/permit/{applyId}")
    public ResponseDto<Boolean> permitApply(
            @PathVariable Long applyId,
            @RequestParam(name = "userId") Long writerId,
            @Valid @RequestBody ApplyRequestDto applyRequestDto) {

        return new ResponseDto<>(applyService.permitApply(writerId,applyId,applyRequestDto));
    }

    //팀 빌딩 팀장이 지원 거절
    @PutMapping("/deny/{applyId}")
    public ResponseDto<Boolean> denyApply(
            @PathVariable Long applyId,
            @RequestParam(name = "userId") Long writerId,
            @Valid @RequestBody ApplyRequestDto applyRequestDto) {

        return new ResponseDto<>(applyService.denyApply(writerId,applyId,applyRequestDto));
    }

    //지원 내역 삭제
    @DeleteMapping("/{applyId}")
    public ResponseDto<Boolean> deleteApply(
            @PathVariable Long applyId,
            @RequestParam(name = "userId") Long userId) {

        return new ResponseDto<>(applyService.deleteApply(applyId,userId));
    }

}
