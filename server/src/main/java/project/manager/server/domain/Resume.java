package project.manager.server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import project.manager.server.dto.request.ResumeRequestDto;
import project.manager.server.dto.request.UserRequestDto;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESEUME_TB")
@DynamicUpdate
public class Resume {
    @Id
    @Column(name = "id",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "job",nullable = false )
    private String job;

    @Builder
    public Resume(String job, User user){
        this.user = user;
        this.job = job;
    }

    public void updateResume(User user, String job){
        this.user = user;
        this.job = job;
    }




}
