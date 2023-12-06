package project.manager.server.controller.post;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.post.building.BuildingPostRequestDto;
import project.manager.server.dto.request.post.building.BuildingPostUpdateDto;
import project.manager.server.service.post.building.ProjectPostService;

@RestController
@RequestMapping("/projectPostPost")
@RequiredArgsConstructor
public class ProjectPostController {

    private final ProjectPostService projectPostService;

    //프로젝트 게시글 생성
    @PostMapping("")
    public ResponseDto<Boolean> createProjectPost(
            @Valid @RequestBody BuildingPostRequestDto projectPostRequestDto) {

        return new ResponseDto<>(projectPostService.createProjectPost(projectPostRequestDto));
    }

    //프로젝트 게시글 목록 읽어오기
    @GetMapping("/list")
    public ResponseDto<Map<String, Object>> readProjectPostList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(projectPostService.readProjectPostList(page, size));
    }

    //프로젝트 게시글 읽어오기
    @GetMapping("/{projectPostId}")
    public ResponseDto<Map<String, Object>> readProjectPost(@PathVariable Long projectPostId) {

        return new ResponseDto<>(projectPostService.readProjectPost(projectPostId));
    }

    //프로젝트 게시글에서 제목, 내용 수정
    @PutMapping("/{projectPostId}")
    public ResponseDto<Boolean> updateProjectPost(
            @PathVariable Long projectPostId,
            @Valid @RequestBody BuildingPostUpdateDto updateDto) {

        return new ResponseDto<>(projectPostService.updateProjectPost(projectPostId, updateDto));
    }

    //프로젝트 게시글 마감
    @PutMapping("/end/{projectPostId}")
    public ResponseDto<Boolean> endProjectPost(@PathVariable Long projectPostId) {

        return new ResponseDto<>(projectPostService.endProjectPost(projectPostId));
    }

    @DeleteMapping("/{projectPostId}")
    public ResponseDto<Boolean> deleteProjectPost(@PathVariable Long projectPostId) {

        return new ResponseDto<>(projectPostService.deleteProjectPost(projectPostId));
    }

}
