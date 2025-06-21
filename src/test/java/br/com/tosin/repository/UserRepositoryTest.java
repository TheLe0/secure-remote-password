package br.com.tosin.repository;

import br.com.tosin.models.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = new User(new BigInteger("123456"), new BigInteger("654321"));
    }

    @Test
    void testSaveAndRetrieveUser() {
        String username = "alice";

        userRepository.save(username, user);
        User retrieved = userRepository.getByUsername(username);

        assertNotNull(retrieved);
        assertEquals(user.salt(), retrieved.salt());
        assertEquals(user.verifier(), retrieved.verifier());
    }

    @Test
    void testGetByUsernameReturnsNullWhenNotFound() {
        User result = userRepository.getByUsername("nonexistent");
        assertNull(result);
    }
}
