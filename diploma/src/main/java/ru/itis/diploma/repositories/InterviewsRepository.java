package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.diploma.models.Interview;

import java.util.List;

@Repository
public interface InterviewsRepository extends JpaRepository<Interview, String> {
    List<Interview> findAllByUserIdOrderByDateTimeDesc(Long id);
}
