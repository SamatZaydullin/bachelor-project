package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.diploma.models.Speciality;

public interface SpecialitiesRepository extends JpaRepository<Speciality, Long> {
}
