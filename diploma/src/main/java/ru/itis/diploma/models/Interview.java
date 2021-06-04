package ru.itis.diploma.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
@Builder
public class Interview {

    @Id
    private String interviewUUID;

    private Integer questionCount;

    @OneToMany(mappedBy = "interview")
    private List<Evaluation> evaluations;

    @ManyToMany
    private List<Theme> themes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Instant dateTime;
}
