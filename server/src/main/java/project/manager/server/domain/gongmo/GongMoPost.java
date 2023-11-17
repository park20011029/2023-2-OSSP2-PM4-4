package project.manager.server.domain.gongmo;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.domain.Board;
import project.manager.server.domain.User;
import project.manager.server.domain.building.BuildingPost;
import project.manager.server.enums.PostState;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "GONGMOPOST")
@DynamicUpdate
public class GongMoPost {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "state")
    private PostState postState;

    @Column(name = "create_at")
    private Timestamp createAt;

    // -----------------------------------------------------
    @OneToOne(mappedBy = "gongMoPost", fetch = FetchType.LAZY)
    private GongCaMap gongCaMap;

    @OneToMany(mappedBy = "gongMoPost", fetch = FetchType.LAZY)
    private List<BuildingPost> buildingPostList;

    @Builder
    public GongMoPost(
            User user,
            Board board,
            String title,
            String content,
            LocalDate startAt,
            LocalDate endAt) {
        this.user = user;
        this.board = board;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.postState = PostState.on;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
        this.isDelete = false;
    }


}
