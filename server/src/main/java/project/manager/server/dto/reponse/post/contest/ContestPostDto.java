package project.manager.server.dto.reponse.post.contest;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.post.contest.ContestPost;

@Getter
@NoArgsConstructor
public class ContestPostDto {
    private Long userId;
    private String user;
    private BenefitDto benefit;
    private CategoryDto category;
    private OrganizationDto organization;
    private ScaleDto scale;
    private TargetDto target;
    private String title;
    private String content;
    private LocalDate startAt;
    private LocalDate endAt;
    private String imageUrl;

    @Builder
    public ContestPostDto(ContestPost contestPost) {
        this.userId = contestPost.getWriter().getId();
        this.user = contestPost.getWriter().getNickName();
        this.benefit = BenefitDto.builder().benefit(contestPost.getBenefit()).build();
        this.category = CategoryDto.builder().category(contestPost.getCategory()).build();
        this.organization = OrganizationDto.builder().organization(contestPost.getOrganization()).build();
        this.scale = ScaleDto.builder().scale(contestPost.getScale()).build();
        this.target = TargetDto.builder().target(contestPost.getTarget()).build();
        this.title = contestPost.getTitle();
        this.content = contestPost.getContent();
        this.startAt = contestPost.getStartAt();
        this.endAt = contestPost.getEndAt();
        this.imageUrl = contestPost.getContestImage().getUrl();
    }
}
