package project.manager.server.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.post.contest.Organization;
import project.manager.server.dto.reponse.post.contest.OrganizationDto;
import project.manager.server.dto.reponse.post.contest.TargetDto;
import project.manager.server.dto.request.post.contest.OrganizationRequestDto;
import project.manager.server.repository.post.contast.OrganizationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
