package project.manager.server.service.gongmo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.gongmo.*;
import project.manager.server.dto.reponse.gongmo.GongCaListDto;
import project.manager.server.dto.request.gongmo.GongCaMapRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.gongmo.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GongCaMapService {
    private final GongMoPostRepository gongMoPostRepository;
    private final GongCaMapRepository gongCaMapRepository;
    private final BenefitRepository benefitRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizationRepository organizationRepository;
    private final ScaleRepository scaleRepository;
    private final TargetRepository targetRepository;

    private final BenefitService benefitService;
    private final CategoryService categoryService;
    private final OrganizationService organizationService;
    private final ScaleService scaleService;
    private final TargetService targetService;


    @Transactional
    public Long createGongCa(Long gongMoPostId, GongCaMapRequestDto gongCaMapRequestDto) {


        GongMoPost gongMoPost =
                gongMoPostRepository
                        .findById(gongMoPostId)
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        System.err.println("1");
        Benefit benefit =
                benefitRepository.findById(gongCaMapRequestDto.getBenefitId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND1));
        System.err.println(benefit.getBenefitType());
        Category category =
                categoryRepository
                        .findById(gongCaMapRequestDto.getCategoryId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND2));
        System.err.println(gongCaMapRequestDto.getBenefitId());
        Organization organization =
                organizationRepository
                        .findById(gongCaMapRequestDto.getOrganizationId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND3));
        System.err.println(organization.getOrganizationType());
        Scale scale =
                scaleRepository
                        .findById(gongCaMapRequestDto.getScaleId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND4));
        System.err.println(scale.getScaleType());
        Target target =
                targetRepository
                        .findById(gongCaMapRequestDto.getTargetId())
                        .orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND4));
        System.err.println(target.getTargetType());
        GongCaMap newGongCaMap = GongCaMap.builder()
                .gongMoPost(gongMoPost)
                .benefit(benefit)
                .category(category)
                .organization(organization)
                .scale(scale)
                .target(target).build();
        System.err.println("7");
        gongCaMapRepository.save(newGongCaMap);
        System.err.println("8");
        return newGongCaMap.getId();
    }

    public GongCaListDto readGongCaList() {
        return GongCaListDto.builder()
                .benefit(benefitService.readBenefit())
                .category(categoryService.readCategory())
                .organization(organizationService.readOrganization())
                .scale(scaleService.readScale())
                .target(targetService.readTarget())
                .build();
    }
}















