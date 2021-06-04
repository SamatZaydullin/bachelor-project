package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.diploma.models.Theme;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemesRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findThemeByTitle(String title);
    List<Theme> findAllBySpecialityId(Long id);
}
