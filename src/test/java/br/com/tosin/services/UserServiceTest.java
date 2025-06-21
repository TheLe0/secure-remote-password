package br.com.tosin.services;

import br.com.tosin.profiles.MockUserRepositoryProfile;
import br.com.tosin.models.User;
import br.com.tosin.mocks.MockUserRepository;
import br.com.tosin.utils.SRPUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestProfile(MockUserRepositoryProfile.class)
public class UserServiceTest {

    @Inject
    UserService userService;

    @Inject
    MockUserRepository mockUserRepository;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        mockUserRepository.clear();
    }

    @Test
    void testAuthenticateSuccess() {
        String username = "alice";
        String password = "pass123";
        User user = SRPUtil.encryptUser(username, password);

        mockUserRepository.save(username, user);
        outContent.reset();

        userService.authenticate(username, password);

        assertTrue(outContent.toString().contains("[LOGIN SUCCESSFUL]"));
    }

    @Test
    void testAuthenticateFailsWithInvalidPassword() {
        String username = "bob";
        String correct = "correct";
        String wrong = "wrong";
        User user = SRPUtil.encryptUser(username, correct);

        mockUserRepository.save(username, user);
        outContent.reset();

        userService.authenticate(username, wrong);

        assertTrue(outContent.toString().contains("[LOGIN FAILED]"));
    }

    @Test
    void testRegister() {
        userService.register("test", "123");
        assertTrue(outContent.toString().contains("[REGISTERED]"));
        assertNotNull(mockUserRepository.getByUsername("test"));
    }
}
