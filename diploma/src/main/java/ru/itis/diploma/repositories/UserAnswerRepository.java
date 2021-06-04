package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.diploma.models.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}
