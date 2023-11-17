package project.manager.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.building.BuildingPostRequestDto;
import project.manager.server.service.Building.BuildingPostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/building")
public class BuildingPostController {
//    private final BuildingPostService buildingPostService;
//
//    @PostMapping("/post/{gongMoPostID}")
//    public ResponseDto<Long> createBuildingPost(@PathVariable Long gongMoPostID, @RequestBody BuildingPostRequestDto buildingPostRequestDto){
//        return new ResponseDto<Long>(buildingPostService.createBuilingPost(gongMoPostID, buildingPostRequestDto));
//    }
}
