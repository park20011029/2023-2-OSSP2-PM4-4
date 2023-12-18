package project.manager.server.controller.post;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import project.manager.server.dto.reponse.ResponseDto;
import project.manager.server.dto.reponse.post.contest.ContestPostDto;
import project.manager.server.dto.request.post.contest.ContestPostRequestDto;
import project.manager.server.dto.request.report.ContestReportRequestDto;
import project.manager.server.service.post.contest.ContestPostService;
import project.manager.server.service.report.ContestReportService;


@RestController
@RequestMapping("/contestPost")
@RequiredArgsConstructor
public class ContestPostController {

    private final ContestPostService contestPostService;
    private final ContestReportService contestReportService;

    //공모전 게시글 생성
    @PostMapping("")
    public ResponseDto<Boolean> createContestPost(
            @Valid @RequestPart ContestPostRequestDto contestPostRequestDto,
            @RequestPart MultipartFile file) {

        return new ResponseDto<>(contestPostService.createContestPost(contestPostRequestDto, file));
    }

    @PostMapping("/report")
    public ResponseDto<Boolean> contestPostReport(@Valid @RequestBody ContestReportRequestDto contestReportRequestDto) {

        return new ResponseDto<>(contestReportService.creatContestReport(contestReportRequestDto));
    }

    //공모전 게시글 목록 읽어오기
    //request param으로 카테고리들 다 받아오고,
    @GetMapping("")
    public ResponseDto<Map<String,Object>> readContestPostList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(contestPostService.readContestList(page, size));
    }

    @GetMapping("/search/{keyWord}")
    public ResponseDto<Map<String,Object>> findContestPostList(
            @PathVariable String keyWord,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size,
            @RequestParam(name = "scale", required = false) List<Long> scale,
            @RequestParam(name = "category", required = false) List<Long> category,
            @RequestParam(name = "organization", required = false) List<Long> organization,
            @RequestParam(name = "target", required = false) List<Long> target,
            @RequestParam(name = "benefit", required = false) List<Long> benefit) {

        return new ResponseDto<>(contestPostService.findContestPostList(keyWord, page, size, scale, category, organization, target, benefit));
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
            @Valid @RequestPart ContestPostRequestDto contestPostRequestDto,
            @RequestPart MultipartFile file) {

        return new ResponseDto<>(contestPostService.updateContestPost(contestPostId, contestPostRequestDto, file));
    }

}
