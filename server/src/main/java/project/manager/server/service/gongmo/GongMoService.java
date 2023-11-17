package project.manager.server.service.gongmo;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.Board;
import project.manager.server.domain.gongmo.GongMoPost;
import project.manager.server.domain.User;
import project.manager.server.dto.reponse.gongmo.GongMoListDto;
import project.manager.server.dto.request.gongmo.GongMoPostRequestDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.BoardRepository;
import project.manager.server.repository.gongmo.GongMoPostRepository;
import project.manager.server.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class GongMoService {
    private final GongMoPostRepository gongMoPostRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Boolean createGongMoPost(GongMoPostRequestDto gongMoPostRequestDto) {
        User user =
                userRepository
                        .findByNickName(gongMoPostRequestDto.getNickname())
                        .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Board board =
                boardRepository
                        .findByBoardName(gongMoPostRequestDto.getBoardName())
                        .orElseThrow(() -> new ApiException(ErrorDefine.BOARD_NOT_FOUND));

        gongMoPostRepository.save(
                GongMoPost.builder()
                        .user(user)
                        .board(board)
                        .title(gongMoPostRequestDto.getTitle())
                        .content(gongMoPostRequestDto.getContent())
                        .startAt(gongMoPostRequestDto.getStartAt())
                        .endAt(gongMoPostRequestDto.getEndAt())
                        .build());
        return Boolean.TRUE;
    }

    public List<GongMoListDto> getGongMoPost(String boardName) {
        Board board =
                boardRepository
                        .findByBoardName(boardName)
                        .orElseThrow(() -> new ApiException(ErrorDefine.BOARD_NOT_FOUND));

        List<GongMoPost> gongMoPostList = gongMoPostRepository.findByBoardId(board.getId());

        List<GongMoListDto> gongMoPostDto = new ArrayList<>();

        for (GongMoPost gongMoPost1 : gongMoPostList) {
            gongMoPostDto.add(
                    GongMoListDto.builder()
                            .startAt(gongMoPost1.getStartAt())
                            .endAt(gongMoPost1.getEndAt())
                            .boardId(board.getId())
                            .title(gongMoPost1.getTitle())
                            .build());
        }
        return gongMoPostDto;
    }
}
