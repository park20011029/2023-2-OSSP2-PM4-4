package project.manager.server.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import project.manager.server.dto.ResponseDto;
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

    @PostMapping("/benefit/{benefit}")
    ResponseDto<Boolean> createBenefit(@PathVariable String benefit) {
        return new ResponseDto<>(benefitService.createBenefit(benefit));
    }

    @PostMapping("/category/{category}")
    ResponseDto<Boolean> createCategory(@PathVariable String category) {
        return new ResponseDto<>(categoryService.createCategory(category));
    }

    @PostMapping("/organization/{organization}")
    ResponseDto<Boolean> createOrganization(@PathVariable String organization) {
        return new ResponseDto<>(organizationService.createOrganization(organization));
    }

    @PostMapping("/scale/{scale}")
    ResponseDto<Boolean> createScale(@PathVariable String scale) {
        return new ResponseDto<>(scaleService.createScale(scale));
    }

    @PostMapping("/target/{target}")
    ResponseDto<Boolean> createTarget(@PathVariable String target) {
        return new ResponseDto<>(targetService.createTarget(target));
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
