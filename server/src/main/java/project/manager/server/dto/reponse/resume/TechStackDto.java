package project.manager.server.dto.reponse.resume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.manager.server.domain.resume.TechStack;

@Getter
@AllArgsConstructor
public class TechStackDto {
    Long id;
    String techType;
    String description;
    String tech;

    @Builder
    public TechStackDto(TechStack techStack) {
        this.id = techStack.getId();
        this.techType = techStack.getTechType().getLabel();
        this.description = techStack.getDescription();
        this.tech = techStack.getTech();
    }
}
