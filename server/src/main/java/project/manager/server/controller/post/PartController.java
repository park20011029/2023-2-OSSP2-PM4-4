package project.manager.server.controller.post;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.request.post.building.PartRequestDto;
import project.manager.server.service.post.building.PartService;

@RestController
@RequestMapping("/part")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    //팀빌딩 게시글 수정에서 모집역할군 추가
    @PostMapping("/{buildingPostId}")
    public ResponseDto<Boolean> createPart(
            @PathVariable Long buildingPostId,
            @Valid @RequestBody PartRequestDto partRequestDto) {

        return new ResponseDto<>(partService.createPart(buildingPostId, partRequestDto));
    }

    //팀빌딩 게시글 수정에서 모집역할군의 최대모집인원 수정
    @PutMapping("/{partId}")
    public ResponseDto<Boolean> fixMaxNum(@PathVariable Long partId, @RequestBody Map<String, Integer> body) {

        return new ResponseDto<>(partService.fixMaxApplicant(partId, body.get("fixNum")));
    }

    @DeleteMapping("/{partId}")
    public ResponseDto<Boolean> deletePart(@PathVariable Long partId) {

        return new ResponseDto<>(partService.deletePart(partId));
    }

}
