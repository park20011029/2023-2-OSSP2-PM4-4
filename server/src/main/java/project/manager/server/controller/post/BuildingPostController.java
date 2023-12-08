package project.manager.server.controller.post;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.post.building.BuildingPostRequestDto;
import project.manager.server.dto.request.post.building.BuildingPostUpdateDto;
import project.manager.server.dto.request.report.BuildingReportRequestDto;
import project.manager.server.service.post.building.BuildingPostService;
import project.manager.server.service.report.BuildingReportService;

@RestController
@RequestMapping("/buildingPost")
@RequiredArgsConstructor
public class BuildingPostController {

    private final BuildingPostService buildingPostService;
    private final BuildingReportService buildingReportService;

    //공모전에서 팀빌딩 게시글 생성
    @PostMapping("/{contestPostId}")
    public ResponseDto<Boolean> createBuildingPost(
            @PathVariable Long contestPostId,
            @Valid @RequestBody BuildingPostRequestDto buildingPostRequestDto) {

        return new ResponseDto<>(buildingPostService.createBuildingPost(contestPostId,buildingPostRequestDto));
    }

    @PostMapping("/report")
    public ResponseDto<Boolean> buildingPostReport(@Valid @RequestBody BuildingReportRequestDto buildingReportRequestDto) {

        return new ResponseDto<>(buildingReportService.creatBuildingReport(buildingReportRequestDto));
    }

    //팀빌딩 게시글 목록 읽어오기
    @GetMapping("/list/{contestPostId}")
    public ResponseDto<Map<String, Object>> readBuildingPostList(
            @PathVariable Long contestPostId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(buildingPostService.readBuildingPostList(contestPostId, page, size));
    }

    //팀빌딩 게시글 읽어오기
    @GetMapping("/{buildingPostId}")
    public ResponseDto<Map<String, Object>> readBuildingPost(@PathVariable Long buildingPostId) {

        return new ResponseDto<>(buildingPostService.readBuildingPost(buildingPostId));
    }

    //팀빌딩 게시글에서 제목, 내용 수정
    @PutMapping("/{buildingPostId}")
    public ResponseDto<Boolean> updateBuildingPost(
            @PathVariable Long buildingPostId,
            @Valid @RequestBody BuildingPostUpdateDto updateDto) {

        return new ResponseDto<>(buildingPostService.updateBuildingPost(buildingPostId, updateDto));
    }

    //팀빌딩 게시글 마감
    @PutMapping("/end/{buildingPostId}")
    public ResponseDto<Boolean> endBuildingPost(@PathVariable Long buildingPostId) {

        return new ResponseDto<>(buildingPostService.endBuildingPost(buildingPostId));
    }

    //팀빌딩 게시글 삭제
    @DeleteMapping("{buildingPostId}")
    public ResponseDto<Boolean> deleteBuildingPost(@PathVariable Long buildingPostId) {

        return new ResponseDto<>(buildingPostService.deleteBuildingPost(buildingPostId));
    }
}
