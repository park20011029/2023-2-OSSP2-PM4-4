package project.manager.server.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import project.manager.server.domain.post.building.BuildingPost;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "REVIEW_TB")
@DynamicUpdate
public class Review {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "partName")
    private String partName;

    @Column(name = "score")
    private Double score;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "is_delete")
    private boolean isDelete;

    // -------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingpost_id")
    private BuildingPost buildingPost;

    // -------------------------------------------------------------------

    @Builder
    public Review(User reviewer, User reviewee, BuildingPost buildingPost, String partName) {
        this.partName = partName;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.buildingPost = buildingPost;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.isDelete = false;
    }

    public void updateReview(String content, Double score) {
        this.content = content;
        this.score = score;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void deleteReview() {
        this.isDelete = true;
    }

}
