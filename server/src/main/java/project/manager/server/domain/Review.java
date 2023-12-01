package project.manager.server.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "score", nullable = false)
    private Double score;

    @Column(name = "created_date")
    private Timestamp createdDate;

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

    public Review(String content, Double score, User reviewer, User reviewee, BuildingPost buildingPost) {
        this.content = content;
        this.score = score;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.buildingPost = buildingPost;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }

}
