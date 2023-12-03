package project.manager.server.domain.post.building;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.User;
import project.manager.server.domain.post.contest.ContestPost;
import project.manager.server.dto.request.post.building.BuildingPostRequestDto;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "BUILDING_POST_TB")
@DynamicUpdate
public class BuildingPost {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "is_recruiting")
    private boolean isRecruiting;

    //---------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contestpost_id", nullable = false)
    private ContestPost contestPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(mappedBy = "buildingPost", fetch = FetchType.LAZY)
    private List<Part> parts;

    //------------------------------------------------------------

    @Builder
    public BuildingPost(BuildingPostRequestDto buildingPostRequestDto, ContestPost contestPost, User writer) {
        this.title = buildingPostRequestDto.getTitle();
        this.content = buildingPostRequestDto.getContent();
        this.isDelete = false;
        this.isRecruiting = true;
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
        this.contestPost = contestPost;
        this.writer = writer;
    }

    public void updateBuildingPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void buildingPostClose() {
        this.isRecruiting = false;
    }

    public void deleteBuildingPost() {
        this.isRecruiting = false;
        this.isDelete = true;
    }

}
