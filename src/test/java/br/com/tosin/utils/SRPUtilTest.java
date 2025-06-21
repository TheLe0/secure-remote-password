package br.com.tosin.utils;

import br.com.tosin.models.User;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class SRPUtilTest {

    @Test
    void testEncryptUserGeneratesValidUser() {
        String username = "testuser";
        String password = "securepass";

        User user = SRPUtil.encryptUser(username, password);

        assertNotNull(user);
        assertNotNull(user.salt());
        assertNotNull(user.verifier());
        assertTrue(user.salt().compareTo(BigInteger.ZERO) > 0);
        assertTrue(user.verifier().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    void testValidateCredentialsSuccess() {
        String username = "john";
        String password = "mypassword";

        User user = SRPUtil.encryptUser(username, password);
        Boolean result = SRPUtil.validateCredentials(username, password, user);

        assertTrue(result);
    }

    @Test
    void testValidateCredentialsFailureWrongPassword() {
        String username = "john";
        String correct = "mypassword";
        String wrong = "wrongpass";

        User user = SRPUtil.encryptUser(username, correct);
        Boolean result = SRPUtil.validateCredentials(username, wrong, user);

        assertFalse(result);
    }

    @Test
    void testValidateCredentialsFailureWrongUsername() {
        String correctUsername = "john";
        String incorrectUsername = "jane";
        String password = "pass123";

        User user = SRPUtil.encryptUser(correctUsername, password);
        Boolean result = SRPUtil.validateCredentials(incorrectUsername, password, user);

        assertFalse(result);
    }
}
