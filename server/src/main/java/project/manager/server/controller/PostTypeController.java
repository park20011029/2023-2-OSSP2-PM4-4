package project.manager.server.controller;

import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
import project.manager.server.dto.request.post.contest.PostTypeRequestDto;
import project.manager.server.service.post.contest.*;

@RestController
@RequestMapping("/postType")
@RequiredArgsConstructor
public class PostTypeController {

    private final BenefitService benefitService;
    private final CategoryService categoryService;
    private final OrganizationService organizationService;
    private final ScaleService scaleService;
    private final TargetService targetService;

    //공모전 카테고리 중 수상혜택 생성
    @PostMapping("/benefit")
    ResponseDto<Boolean> createBenefit(@Valid @RequestBody PostTypeRequestDto.BenefitDto benefitDto) {

        return new ResponseDto<>(benefitService.createBenefit(benefitDto));
    }

    //공모전 카테고리 중 공모분야 생성
    @PostMapping("/category")
    ResponseDto<Boolean> createCategory(@Valid @RequestBody PostTypeRequestDto.CategoryDto categoryDto) {

        return new ResponseDto<>(categoryService.createCategory(categoryDto));
    }

    //공모전 카테고리 중 주최기관 생성
    @PostMapping("/organization")
    ResponseDto<Boolean> createOrganization(@Valid @RequestBody PostTypeRequestDto.OrganizationDto organizationDto) {

        return new ResponseDto<>(organizationService.createOrganization(organizationDto));
    }

    //공모전 카테고리 중 시상규모 생성
    @PostMapping("/scale")
    ResponseDto<Boolean> createScale(@Valid @RequestBody PostTypeRequestDto.ScaleDto scaleDto) {

        return new ResponseDto<>(scaleService.createScale(scaleDto));
    }

    //공모전 카테고리 중 응모대상 생성
    @PostMapping("/target")
    ResponseDto<Boolean> createTarget(@Valid @RequestBody PostTypeRequestDto.TargetDto targetDto) {

        return new ResponseDto<>(targetService.createTarget(targetDto));
    }

    //수상혜택 목록 읽어오기
    @GetMapping("/benefit")
    ResponseDto<Map<String, Object>> readBenefitList() {

        return new ResponseDto<>(benefitService.readBenefitList());
    }

    //공모분야 목록 읽어오기
    @GetMapping("/category")
    ResponseDto<Map<String, Object>> readCategoryList() {

        return new ResponseDto<>(categoryService.readCategoryList());
    }

    //주최기관 목록 읽어오기
    @GetMapping("/organization")
    ResponseDto<Map<String, Object>> readOrganzationList() {

        return new ResponseDto<>(organizationService.readOrganizationList());
    }

    //시상규모 목록 읽어오기
    @GetMapping("/scale")
    ResponseDto<Map<String, Object>> readScaleList() {

        return new ResponseDto<>(scaleService.readScaleList());
    }

    //응모대상 목록 읽어오기
    @GetMapping("/target")
    ResponseDto<Map<String, Object>> readTargetList() {

        return new ResponseDto<>(targetService.readTargetList());
    }

}
