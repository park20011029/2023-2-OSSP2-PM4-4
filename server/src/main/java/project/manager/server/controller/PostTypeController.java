package project.manager.server.controller;

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

    @PostMapping("/benefit")
    ResponseDto<Boolean> createBenefit(@RequestBody PostTypeRequestDto.BenefitDto benefitDto) {
        return new ResponseDto<>(benefitService.createBenefit(benefitDto));
    }

    @PostMapping("/category")
    ResponseDto<Boolean> createCategory(@RequestBody PostTypeRequestDto.CategoryDto categoryDto) {
        return new ResponseDto<>(categoryService.createCategory(categoryDto));
    }

    @PostMapping("/organization")
    ResponseDto<Boolean> createOrganization(@RequestBody PostTypeRequestDto.OrganizationDto organizationDto) {
        return new ResponseDto<>(organizationService.createOrganization(organizationDto));
    }

    @PostMapping("/scale")
    ResponseDto<Boolean> createScale(@RequestBody PostTypeRequestDto.ScaleDto scaleDto) {
        return new ResponseDto<>(scaleService.createScale(scaleDto));
    }

    @PostMapping("/target")
    ResponseDto<Boolean> createTarget(@RequestBody PostTypeRequestDto.TargetDto targetDto) {
        return new ResponseDto<>(targetService.createTarget(targetDto));
    }

    @GetMapping("/benefit")
    ResponseDto<Map<String, Object>> readBenefitList() {
        return new ResponseDto<>(benefitService.readBenefitList());
    }

    @GetMapping("/category")
    ResponseDto<Map<String, Object>> readCategoryList() {
        return new ResponseDto<>(categoryService.readCategoryList());
    }

    @GetMapping("/organization")
    ResponseDto<Map<String, Object>> readOrganzationList() {
        return new ResponseDto<>(organizationService.readOrganizationList());
    }

    @GetMapping("/scale")
    ResponseDto<Map<String, Object>> readScaleList() {
        return new ResponseDto<>(scaleService.readScaleList());
    }

    @GetMapping("/target")
    ResponseDto<Map<String, Object>> readTargetList() {
        return new ResponseDto<>(targetService.readTargetList());
    }

}
