package project.manager.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.post.building.ApplyRequestDto;
import project.manager.server.service.post.building.ApplyService;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    final private ApplyService applyService;

    @PostMapping("")
    public ResponseDto<Boolean> creatApply(@Valid @RequestBody ApplyRequestDto applyRequestDto) {

        return new ResponseDto<>(applyService.createApply(applyRequestDto));
    }

    @PutMapping("/permit/{applyId}")
    public ResponseDto<Boolean> permitApply(
            @PathVariable Long applyId,
            @RequestParam(name = "userId") Long writerId,
            @Valid @RequestBody ApplyRequestDto applyRequestDto) {

        return new ResponseDto<>(applyService.permitApply(writerId,applyId,applyRequestDto));
    }

    @PutMapping("/deny/{applyId}")
    public ResponseDto<Boolean> denyApply(
            @PathVariable Long applyId,
            @RequestParam(name = "userId") Long writerId,
            @Valid @RequestBody ApplyRequestDto applyRequestDto) {

        return new ResponseDto<>(applyService.denyApply(writerId,applyId,applyRequestDto));
    }

    @DeleteMapping("/{applyId}")
    public ResponseDto<Boolean> deleteApply(
            @PathVariable Long applyId,
            @RequestParam(name = "userId") Long userId) {

        return new ResponseDto<>(applyService.deleteApply(applyId,userId));
    }

}
