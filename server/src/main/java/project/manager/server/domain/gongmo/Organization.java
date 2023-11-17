package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.enums.gongmo.OrganizationType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ORGANIZATION_TB")
@DynamicUpdate
public class Organization {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization")
    @Enumerated(EnumType.STRING)
    private OrganizationType organizationType;

    @Builder
    public Organization(Organization organization){
        this.organizationType = organization.getOrganizationType();
    }
}
