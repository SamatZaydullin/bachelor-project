package ru.itis.diploma.models;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Enumerated(EnumType.STRING)
    private Complexity complexity;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "question")
    private List<Evaluation> evaluation;
}
