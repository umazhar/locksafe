package model;

import model.Encryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//tests encryptor
public class EncryptorTest {
    @Test
    void testSmallPassword() {
        String password = "3.zc1";
        String encryptedPassword = Encryptor.encrypt(password);
        assertNotEquals(encryptedPassword, password);
        String decryptedPassword = Encryptor.decrypt(encryptedPassword);
        assertEquals(password, decryptedPassword);
    }

    @Test
    void testLargePassword() {
        String password = "9012`1~.,p['3huxczjbu/[p]p[";
        String encryptedPassword = Encryptor.encrypt(password);
        assertNotEquals(encryptedPassword, password);
        String decryptedPassword = Encryptor.decrypt(encryptedPassword);
        assertEquals(password, decryptedPassword);
    }
}
