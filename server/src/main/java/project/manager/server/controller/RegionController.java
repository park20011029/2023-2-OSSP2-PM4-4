package project.manager.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.reponse.region.GuDto;
import project.manager.server.dto.reponse.region.SiDto;
import project.manager.server.service.RegionService;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping("/si")
    public ResponseDto<List<SiDto>> showSiList() {

        return new ResponseDto<List<SiDto>>(regionService.readSiList());
    }

    @GetMapping("/gu/{siId}")
    public ResponseDto<List<GuDto>> showGuList(@PathVariable Long siId) {

        return new ResponseDto<List<GuDto>>(regionService.readGuList(siId));
    }
}
