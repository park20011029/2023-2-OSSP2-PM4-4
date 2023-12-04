package project.manager.server.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.service.ReviewService;
import project.manager.server.service.post.building.ApplyService;
import project.manager.server.service.post.building.BuildingPostService;
import project.manager.server.service.post.building.ProjectPostService;
import project.manager.server.service.post.contest.ContestPostService;

@RestController
@RequestMapping("/mypost")
@RequiredArgsConstructor
public class MyPageController {
    private final BuildingPostService buildingPostService;
    private final ContestPostService contestPostService;
    private final ApplyService applyService;
    private final ReviewService reviewService;
    private final ProjectPostService projectPostService;

    //내가 쓴 공모글 읽어오기
    @GetMapping("/contest/{userId}")
    public ResponseDto<Map<String, Object>> readMyContestPostList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(contestPostService.readMyContestList(userId, page, size));
    }

    //내가 쓴 팀빌딩 게시글 읽어오기
    @GetMapping("/building/{userId}")
    public ResponseDto<Map<String, Object>> readMyBuildingPostList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(buildingPostService.readMyBuildingList(userId, page, size));
    }
    //내가 지원한 팀 빌딩 포스트 필요

    //내가 작성한 프로젝트 포스트 읽어오기
    @GetMapping("/project/{userId}")
    public ResponseDto<Map<String, Object>> readMyProjectPostList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(projectPostService.readMyProjectList(userId, page, size));
    }

    //내가 받은 리뷰 리스트 읽어오기
    @GetMapping("/myReview/{userId}")
    public ResponseDto<Map<String, Object>> readMyReviewList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(reviewService.readMyReviewList(userId, page, size));
    }

    //내가 작성한 리뷰 리스트 읽어오기
    @GetMapping("/writtenReview/{userId}")
    public ResponseDto<Map<String, Object>> readWrittenReviewList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(reviewService.readWrittenReviewList(userId, page, size));
    }

    //내가 작성해야 할 리뷰 리스트 읽어오기
    @GetMapping("/pendingReview/{userId}")
    public ResponseDto<Map<String, Object>> readPendingReviewList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(reviewService.readPendingReviewList(userId, page, size));
    }

    //나의 지원 목록 읽어오기
    @GetMapping("/apply/{userId}")
    public ResponseDto<Map<String, Object>> readMyApplyList(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(applyService.readMyApplyList(userId, page, size));
    }


}
