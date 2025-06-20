package br.com.tosin.utils;

import br.com.tosin.models.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class SRPUtil {
    private static final BigInteger N = new BigInteger("""
        FFFFFFFF FFFFFFFF C90FDAA2 2168C234 C4C6628B 80DC1CD1
        29024E08 8A67CC74 020BBEA6 3B139B22 514A0879 8E3404DD
        EF9519B3 CD3A431B 302B0A6D F25F1437 4FE1356D 6D51C245
        E485B576 625E7EC6 F44C42E9 A637ED6B 0BFF5CB6 F406B7ED
        EE386BFB 5A899FA5 AE9F2411 7C4B1FE6 49286651 ECE65381
        FFFFFFFF FFFFFFFF
    """.replaceAll("\\s", ""), 16);

    private static final BigInteger g = BigInteger.valueOf(2);
    private static final SecureRandom random = new SecureRandom();

    private static BigInteger hash(byte[]... inputs) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (byte[] input : inputs) digest.update(input);
            return new BigInteger(1, digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static BigInteger hash(BigInteger... values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (BigInteger v : values) digest.update(v.toByteArray());
            return new BigInteger(1, digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean validateCredentials(String username, String password, User storedUser) {

        BigInteger salt = storedUser.salt();
        BigInteger v = storedUser.verifier();
        BigInteger k = SRPUtil.hash(SRPUtil.N, SRPUtil.g);
        BigInteger a = new BigInteger(256, SRPUtil.random);
        BigInteger A = SRPUtil.g.modPow(a, SRPUtil.N);

        BigInteger b = new BigInteger(256, SRPUtil.random);
        BigInteger B = k.multiply(v).add(SRPUtil.g.modPow(b, SRPUtil.N)).mod(SRPUtil.N);

        BigInteger u = SRPUtil.hash(A, B);

        BigInteger x = SRPUtil.hash(salt.toByteArray(), SRPUtil.hash((username + ":" + password).getBytes()).toByteArray());
        BigInteger S_client = B.subtract(k.multiply(SRPUtil.g.modPow(x, SRPUtil.N)))
                .modPow(a.add(u.multiply(x)), SRPUtil.N);
        BigInteger K_client = SRPUtil.hash(S_client);

        BigInteger S_server = A.multiply(v.modPow(u, SRPUtil.N)).modPow(b, SRPUtil.N);
        BigInteger K_server = SRPUtil.hash(S_server);

        BigInteger M1_client = SRPUtil.hash(A, B, K_client);
        BigInteger M1_server = SRPUtil.hash(A, B, K_server);

        return M1_client.equals(M1_server);
    }

    public static User encryptUser(String username, String password) {
        BigInteger salt = new BigInteger(128, SRPUtil.random);
        BigInteger x = SRPUtil.hash(salt.toByteArray(), SRPUtil.hash((username + ":" + password).getBytes()).toByteArray());
        BigInteger v = SRPUtil.g.modPow(x, SRPUtil.N);

        return new User(salt, v);
    }
}
