package project.manager.server.dto.reponse.resume;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import project.manager.server.domain.resume.Award;

@Getter
@NoArgsConstructor
public class AwardDto {

    private Long id;
    private String competition;
    private String awardType;
    private LocalDate awardYear;
    private String awardImage;

    @Builder
    public AwardDto(Award award) {
        this.id = award.getId();
        this.awardImage = award.getAwardImage().getUrl();
        this.competition = award.getCompetition();
        this.awardType = award.getAwardType();
        this.awardYear = award.getAwardYear();
    }
}
