package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.diploma.models.Question;

public interface QuestionsRepository extends JpaRepository<Question, Long> {
}
