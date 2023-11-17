package project.manager.server.domain.building;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.domain.gongmo.GongMoPost;
import project.manager.server.domain.User;
import project.manager.server.dto.request.building.BuildingPostRequestDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BUILING_POST")
@DynamicUpdate
public class BuildingPost {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gongmopost_id",nullable = false)
    private GongMoPost gongMoPost;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "is_delete",nullable = false)
    private Boolean isDelete;

    @Column(name = "create_at")
    private Timestamp createAt;

    //---------------------------------------------------------


    //------------------------------------------------------------

    @Builder
    public BuildingPost(String title, String content,GongMoPost gongMoPost, User user, Boolean isDelete, Boolean status, Timestamp createAt ){
        this.user = user;
        this.gongMoPost = gongMoPost;
        this.title = title;
        this.content = content;
        this.status = Boolean.TRUE;
        this.isDelete = Boolean.FALSE;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
