package monsters.model;

import lombok.*;
import monsters.enums.Gender;
import monsters.enums.Job;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "monster")
public class MonsterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "shouldn't be null")
    @Column(name = "user_id")
    private UUID userId;

    @NotNull(message = "shouldn't be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "job")
    private Job job;

    @NotBlank(message = "shouldn't be empty")
    @Size(max = 16, message = "shouldn't exceed 16 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "shouldn't be null")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "shouldn't be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotBlank(message = "shouldn't be null")
    @Size(max = 30, message = "shouldn't exceed 30 characters")
    @Email
    @Column(name = "email")
    private String email;

    @NotNull(message = "shouldn't be null")
    @Min(value = 0, message = "shouldn't be less than 0")
    @Column(name = "salary")
    private int salary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monster_reward",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    @NotNull(message = "shouldn't be null")
    @ToString.Exclude
    private List<RewardEntity> rewards;

}
