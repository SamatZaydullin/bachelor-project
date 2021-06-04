package ru.itis.diploma.security.encryption;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordEncoder implements PasswordEncoder {

    private static final Argon2 ARGON2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id);

    private static final int ITERATIONS = 4;
    private static final int MEMORY = 250000;
    private static final int PARALLELISM = 4;

    public String encode(final CharSequence rawPassword) {
        return ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM,
                rawPassword.toString().toCharArray());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ARGON2.verify(
                encodedPassword,
                rawPassword.toString().toCharArray()
        );
    }

}
