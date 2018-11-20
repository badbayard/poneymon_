package fr.univ_lyon1.info.m1.poneymon_fx.network.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * A utility class to hash passwords and check passwords vs hashed values. It uses a combination of
 * hashing and unique salt. The algorithm used is PBKDF2WithHmacSHA1 which, although not the best
 * for hashing password (vs. bcrypt) is still considered robust.
 * The hashed value has 256 bits.
 */
public class Password implements Serializable {

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final byte[] SALT = new byte[16];
    private byte[] passwordBytes;

    /**
     * Generates the salt.
     */
    public Password(char[] password) {
        RANDOM.nextBytes(SALT);
        passwordBytes = hash(password);
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password the password to be hashed
     * @return the hashed password with a pinch of salt
     */
    public byte[] hash(char[] password) {
        PBEKeySpec spec = new PBEKeySpec(password, SALT, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Returns true if the given password and salt match the hashed value, false otherwise.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param pwd the password to check
     * @return true if the given password and salt match the hashed value, false otherwise
     */
    public boolean isExpectedPassword(char[] pwd) {
        byte[] pwdHash = hash(pwd);
        Arrays.fill(pwd, Character.MIN_VALUE);

        if (pwdHash.length != passwordBytes.length) {
            return false;
        }

        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != passwordBytes[i]) {
                return false;
            }
        }
        return true;
    }
}
