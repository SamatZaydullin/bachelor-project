package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.diploma.models.Evaluation;

import java.util.List;

@Repository
public interface EvaluationsRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findAllByInterviewInterviewUUID(String uuid);
}
