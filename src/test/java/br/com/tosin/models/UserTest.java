package br.com.tosin.models;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testRecordFields() {
        BigInteger salt = new BigInteger("123456");
        BigInteger verifier = new BigInteger("654321");

        User user = new User(salt, verifier);

        assertEquals(salt, user.salt());
        assertEquals(verifier, user.verifier());
    }

    @Test
    void testEquality() {
        BigInteger salt = new BigInteger("111");
        BigInteger verifier = new BigInteger("222");

        User user1 = new User(salt, verifier);
        User user2 = new User(salt, verifier);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToStringFormat() {
        User user = new User(new BigInteger("1"), new BigInteger("2"));
        String expected = "User[salt=1, verifier=2]";

        assertEquals(expected, user.toString());
    }
}
