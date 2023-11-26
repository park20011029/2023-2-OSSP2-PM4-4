package project.manager.server.service.post.contest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.domain.post.contest.Organization;
import project.manager.server.dto.reponse.post.contest.OrganizationDto;
import project.manager.server.repository.post.contest.OrganizationRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Boolean createOrganization(String organization) {

        Organization newOrganization = Organization.builder().organization(organization).build();
        organizationRepository.save(newOrganization);

        return  true;
    }

    public Map<String, Object> readOrganizationList() {

        List<Organization> organizations = organizationRepository.findAll();

        return Collections.singletonMap("organizations", organizations.stream()
                .map(organization -> OrganizationDto.builder()
                        .organization(organization)
                        .build())
                .collect(Collectors.toList()));
    }
}
