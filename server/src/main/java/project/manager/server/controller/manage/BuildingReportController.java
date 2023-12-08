package project.manager.server.controller.manage;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.service.post.building.BuildingPostService;
import project.manager.server.service.report.BuildingReportService;

@RestController
@RequestMapping("/buildingPostReport")
@RequiredArgsConstructor
public class BuildingReportController {

    private final BuildingReportService buildingReportService;
    private final BuildingPostService buildingPostService;

    /** 팀 빌딩 게시글 신고 리스트 */
    @GetMapping("")
    ResponseDto<Map<String, Object>> readReportList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {

        return new ResponseDto<>(buildingReportService.readReportList(page, size));
    }

    /** 신고된 팀 빌딩 게시글 */
    @GetMapping("/buildingPost/{contentId}")
    public ResponseDto<Map<String, Object>> readReportedBuilding(@PathVariable Long contentId) {

        return new ResponseDto<>(buildingPostService.readBuildingPost(contentId));
    }

    /** 신고된 회원 처벌 */
    @PutMapping("/execute/{reportId}/{penalty}")
    public ResponseDto<Boolean> executeUser(@PathVariable Long reportId, @PathVariable Long penalty) {

        return new ResponseDto<>(buildingReportService.executeUser(reportId, penalty));
    }

    /** 유저 추방 */
    @PutMapping("/expel/{reportId}")
    public ResponseDto<Boolean> expelUser(@PathVariable Long reportId) {

        return new ResponseDto<>(buildingReportService.expelUser(reportId));
    }

    /** 신고 내역 삭제 */
    @PutMapping("/delete/{reportId}")
    public ResponseDto<Boolean> deleteReport(@PathVariable Long reportId) {

        return new ResponseDto<>(buildingReportService.deleteReport(reportId));
    }
}
