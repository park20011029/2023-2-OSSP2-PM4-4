package project.manager.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.manager.server.domain.User;
import project.manager.server.enums.ResumeEduState;
import project.manager.server.enums.TechType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeRequestDto {
   //private User user;
   private Long userId;
   private String job;
   private String projectName;
   private String projectGit;
   private String projectDescription;
   private String congress;
   private Integer awardYear;
   private String awardType;
   private String school;
   private Integer enroll;
   private ResumeEduState resumeEduState;
   private TechType techType;
   private String techDescription;


}

