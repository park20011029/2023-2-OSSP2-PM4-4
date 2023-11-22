package project.manager.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.post.contest.ContestPostDto;
import project.manager.server.dto.request.post.contest.ContestPostRequestDto;
import project.manager.server.service.post.contast.ContestPostService;

import java.util.Map;


@RestController
@RequestMapping("/contestPost")
@RequiredArgsConstructor
public class ContestPostController {
    private final ContestPostService contestPostService;

    @PostMapping("")
    public ResponseDto<Boolean> userSignUp(@Valid @RequestBody ContestPostRequestDto contestPostRequestDto) {
        return new ResponseDto<>(contestPostService.createContestPost(contestPostRequestDto));
    }

    @GetMapping("")
    public ResponseDto<Map<String,Object>> readContestPostList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size) {
        return new ResponseDto<>(contestPostService.readContestList(page, size));
    }

    @GetMapping("/{contestPostId}")
    public ResponseDto<ContestPostDto> readContestPost(@PathVariable Long contestPostId) {
        return new ResponseDto<>(contestPostService.readContestPost(contestPostId));
    }

    @PutMapping("/{contestPostId}")
    public ResponseDto<Boolean> updateContestPost(
            @PathVariable Long contestPostId,
            @RequestBody ContestPostRequestDto contestPostRequestDto) {
        return new ResponseDto<>(contestPostService.updateContestPost(contestPostId, contestPostRequestDto));
    }

}
