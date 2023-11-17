package project.manager.server.domain.building;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.dto.request.building.BuildingRoleRequestDto;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BUILD_ROLE_MAP_TB")
@DynamicUpdate
public class BuildRoleMap {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingpost_id",nullable = false)
    private BuildingPost buildingPost;

    @Column(name = "total",nullable = false)
    private Integer total; // 전체 모집 인원수

    @Column(name = "head_count",nullable = false)
    private Integer headCount; // 현재 모집인원수

    @Column(name = "block")
    private Boolean block; // 지원 현황

    @Builder
    public BuildRoleMap(Integer total, BuildingPost buildingPost, Role role) {
        this.role = role;
        this.buildingPost = buildingPost;
        this.total = total;
        this.headCount = 0;
        this.block = Boolean.FALSE;
    }



}
