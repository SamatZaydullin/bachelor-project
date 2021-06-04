package ru.itis.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.diploma.models.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByConfirmString(String confirmString);

    @Query("update User u set u.isEnabled=false where u.id=:id")
    Optional<User> disableUserById(Long id);

    @Query("update User u set u.isEnabled=true where u.id=:id")
    Optional<User> enableUserById(Long id);
}
