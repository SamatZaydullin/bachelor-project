package ru.itis.diploma.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.diploma.models.User;
import ru.itis.diploma.repositories.UsersRepository;

import java.util.Optional;

@Service(value = "customUserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> user = usersRepository.findByEmail(username.toLowerCase());

        if (!user.isPresent()) {
            throw new BadCredentialsException(String.format("Bad credentials provided for %s", username));
        }

        return new UserDetailsImpl(user.get());
    }
}
