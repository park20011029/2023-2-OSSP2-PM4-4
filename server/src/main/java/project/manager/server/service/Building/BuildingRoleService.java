package project.manager.server.service.Building;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.building.BuildRoleMap;
import project.manager.server.domain.building.BuildingPost;
import project.manager.server.domain.building.Role;
import project.manager.server.dto.request.building.BuildingRoleRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.building.BuildingRoleMapRepository;
import project.manager.server.repository.building.RoleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingRoleService {
//    private final RoleRepository roleRepository;
//    private final BuildingRoleMapRepository  buildingRoleMapRepository;
//
//    public Long createBuildRole(BuildingRoleRequestDto buildingRoleRequestDto, BuildingPost buildingPost) {
//
//        System.err.println(buildingRoleRequestDto.getRoleId()+"  asdf");
//        Role role = roleRepository.findById(buildingRoleRequestDto.getRoleId())
//                .orElseThrow(()-> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
//
//        BuildRoleMap newBuildRole = BuildRoleMap.builder()
//                .role(role)
//                .buildingPost(buildingPost)
//                .total(buildingRoleRequestDto.getTotal())
//                .build();
//
//        buildingRoleMapRepository.save(newBuildRole);
//
//        return newBuildRole.getId();
//
//    }
}
