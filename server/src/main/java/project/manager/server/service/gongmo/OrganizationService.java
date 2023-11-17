package project.manager.server.service.gongmo;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.gongmo.Organization;
import project.manager.server.dto.reponse.gongmo.OrganizationDto;
import project.manager.server.repository.gongmo.OrganizationRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional
    public List<OrganizationDto> readOrganization() {
        List<OrganizationDto> organizationDtos = new ArrayList<>();
        List<Organization> organizationList = organizationRepository.findAll();

        for (Organization organization : organizationList) {
            organizationDtos.add(OrganizationDto.builder().organization(organization).build());
        }

        return organizationDtos;
    }
}
