package project.manager.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.gongmo.GongCaListDto;
import project.manager.server.dto.reponse.gongmo.GongMoListDto;
import project.manager.server.dto.request.gongmo.GongCaMapRequestDto;
import project.manager.server.dto.request.gongmo.GongMoPostRequestDto;
import project.manager.server.service.gongmo.GongCaMapService;
import project.manager.server.service.gongmo.GongMoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gongmo")
public class GongMoPostController {
    private final GongMoService gongMoService;
    private final GongCaMapService gongCaMapService;

    @PostMapping("/post/{boardId}")
    public ResponseDto<Boolean> createGongMoPost(
            @RequestBody GongMoPostRequestDto gongmoPostRequestDto) {
        return new ResponseDto<Boolean>(gongMoService.createGongMoPost(gongmoPostRequestDto));
    }

    @PostMapping("/gongca/{gongMoPostId}")
    public ResponseDto<Long> createGongCaMap(
            @PathVariable Long gongMoPostId, @RequestBody GongCaMapRequestDto gongCaMapRequestDto) {
        return new ResponseDto<Long>(
                gongCaMapService.createGongCa(gongMoPostId, gongCaMapRequestDto));
    }

    @GetMapping("/gongca")
    public ResponseDto<GongCaListDto> readGongCaList() {
        return new ResponseDto<GongCaListDto>(gongCaMapService.readGongCaList());
    }

    @GetMapping("/{boardName}")
    public ResponseDto<List<GongMoListDto>> getGongMopost(@PathVariable String boardName) {
        return new ResponseDto<List<GongMoListDto>>(gongMoService.getGongMoPost(boardName));
    }
}
