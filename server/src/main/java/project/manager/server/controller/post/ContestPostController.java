package project.manager.server.controller.post;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.post.contest.ContestPostDto;
import project.manager.server.dto.request.post.contest.ContestPostRequestDto;
import project.manager.server.service.post.contest.ContestPostService;


@RestController
@RequestMapping("/contestPost")
@RequiredArgsConstructor
public class ContestPostController {
    private final ContestPostService contestPostService;

    //공모전 게시글 생성
    @PostMapping("")
    public ResponseDto<Boolean> createContestPost(@Valid @RequestBody ContestPostRequestDto contestPostRequestDto) {

        return new ResponseDto<>(contestPostService.createContestPost(contestPostRequestDto));
    }

    //공모전 게시글 목록 읽어오기
    //request param으로 카테고리들 다 받아오고,
    @GetMapping("")
    public ResponseDto<Map<String,Object>> readContestPostList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(contestPostService.readContestList(page, size));
    }

    //공모전 게시글 읽어오기
    @GetMapping("/{contestPostId}")
    public ResponseDto<ContestPostDto> readContestPost(@PathVariable Long contestPostId) {

        return new ResponseDto<>(contestPostService.readContestPost(contestPostId));
    }

    //공모전 게시글 제목 내용 수정
    @PutMapping("/{contestPostId}")
    public ResponseDto<Boolean> updateContestPost(
            @PathVariable Long contestPostId,
            @Valid @RequestBody ContestPostRequestDto contestPostRequestDto) {

        return new ResponseDto<>(contestPostService.updateContestPost(contestPostId, contestPostRequestDto));
    }

}
