package ru.itis.diploma.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String passwordHash;

    private String firstName;
    private String lastName;
    private String middleName;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="roles")
    private Set<Role> roles;

    private String confirmString;

    private Boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<UserAnswer> userAnswer;
}
