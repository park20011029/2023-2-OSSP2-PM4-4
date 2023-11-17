package project.manager.server.service.Building;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.Board;
import project.manager.server.domain.User;
import project.manager.server.domain.building.BuildingPost;
import project.manager.server.domain.gongmo.GongMoPost;
import project.manager.server.dto.reponse.building.BuildingPostDto;
import project.manager.server.dto.request.building.BuildingPostRequestDto;
import project.manager.server.dto.request.building.BuildingRoleRequestDto;
import project.manager.server.dto.request.resume.ProjectRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.BoardRepository;
import project.manager.server.repository.UserRepository;
import project.manager.server.repository.building.BuildingPostRepository;
import project.manager.server.repository.building.PartRepository;
import project.manager.server.repository.building.RoleRepository;
import project.manager.server.repository.gongmo.GongMoPostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingPostService {
//    private final PartRepository partRepository;
//    private final RoleRepository roleRepository;
//    private final BuildingPostRepository buildingPostRepository;
//    @Autowired
//    private final UserRepository userRepository;
//    private final GongMoPostRepository gongMoPostRepository;
//    private final BoardRepository boardRepository;
//    private final BuildingRoleService buildingRoleService;
//
//    @Transactional
//    public Long createBuilingPost(Long gongMoPostId, BuildingPostRequestDto buildingPostRequestDto){
//
//        User user = userRepository.findById(buildingPostRequestDto.getUserId())
//                .orElseThrow(()->new ApiException(ErrorDefine.USER_NOT_FOUND));
//
//
//        GongMoPost gongMoPost = gongMoPostRepository.findById(gongMoPostId)
//                .orElseThrow(()->new ApiException(ErrorDefine.BOARD_NOT_FOUND));
//
//        BuildingPost newBuildingPost = BuildingPost.builder()
//                .user(user)
//                .gongMoPost(gongMoPost)
//                .title(buildingPostRequestDto.getTitle())
//                .content(buildingPostRequestDto.getContent())
//                .build();
//
//        if (buildingPostRequestDto.getBuildingRole() != null) {
//            for (BuildingRoleRequestDto buildingRoleRequestDto : buildingPostRequestDto.getBuildingRole()) {
//                buildingRoleService.createBuildRole(buildingRoleRequestDto, newBuildingPost);
//            }
//        }
//
//        buildingPostRepository.save(newBuildingPost);
//
//        return newBuildingPost.getId();
//    }

    /*public List<BuildingPostDto> getBuildingPost(Long gongMoPostId){
       List<BuildingPost> buildingPostList =
              buildingPostRepository.findByBoardId(gongMoPostId);

       List<BuildingPostDto> buildingPostDto = new ArrayList<>();

       for(BuildingPost buildingPost : buildingPostList){
           if(buildingPost.getIsDelete() == Boolean.FALSE){
               buildingPostDto.add(
                       BuildingPostDto.builder()
                               .title(buildingPost.getTitle())
                               .content(buildingPost.getContent())
                               .createAt(buildingPost.getCreateAt())
                               .writer()
               )
           }
       }
    }*/

}
