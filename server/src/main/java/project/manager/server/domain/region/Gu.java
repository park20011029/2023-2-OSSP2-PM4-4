package project.manager.server.domain.region;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "GU_TB")
public class Gu {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "si_id", nullable = false)
    private Si si;

    @Column(name = "gu", nullable = false, length = 100)
    private String gu;
}
