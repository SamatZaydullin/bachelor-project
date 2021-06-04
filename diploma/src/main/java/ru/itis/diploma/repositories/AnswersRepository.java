package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.diploma.models.Answer;

public interface AnswersRepository extends JpaRepository<Answer, Long> {
}
