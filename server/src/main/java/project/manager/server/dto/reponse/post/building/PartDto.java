package project.manager.server.dto.reponse.post.building;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PartDto {
    private Long partId;
    private String partName;
    private Integer currentApplicant;
    private Integer maxApplicant;

}
