package project.manager.server.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.domain.gongmo.GongMoPost;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BOARD_TB")
@DynamicUpdate
public class Board {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "description")
    private String boardDescription;

    // ------------------------------------------------------------

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<GongMoPost> gongMoPostList = new ArrayList<>();

    @Builder
    public Board(String boardName, String boardDescription) {
        this.boardName = boardName;
        this.boardDescription = boardDescription;
    }

    public void ReadGongMo(List<GongMoPost> gongMoPostList) {
        this.gongMoPostList = gongMoPostList;
    }
}
