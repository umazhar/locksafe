package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//Tests password manager
class PasswordManagerTest {
    private PasswordManager testPasswordManager;

    @BeforeEach
    void runBefore() {
        testPasswordManager = new PasswordManager();
    }

    @Test
    void testAddAccount() {
        assertEquals(0, testPasswordManager.getSize());
        testPasswordManager.addAccount("Gmail", "umair", "tent");
        assertEquals(1, testPasswordManager.getSize());
        testPasswordManager.addAccount("Apple", "umaz", "tent");
        assertEquals(2, testPasswordManager.getSize());
    }

    @Test
    void testChangeAttributes() {
        testPasswordManager.addAccount("Gmail", "umair", "tent");
        testPasswordManager.addAccount("Apple", "umair2", "tent");
        testPasswordManager.addAccount("Instagram", "umair2", "tent");

        testPasswordManager.changeAccountAttribute(0, "website", "Hotmail");
        testPasswordManager.changeAccountAttribute(1, "password", "mountain");
        testPasswordManager.changeAccountAttribute(2, "username", "felix");
        testPasswordManager.changeAccountAttribute(3, "notAUsername", "WSJ");

        assertEquals("Hotmail", testPasswordManager.getWebsiteByIndex(0));
        assertEquals("mountain", testPasswordManager.getPasswordByIndex(1));
        assertEquals("felix", testPasswordManager.getUsernameByIndex(2));
    }

    @Test
    void testRemoveAccount() {
        testPasswordManager.addAccount("Gmail", "umair", "tent");
        testPasswordManager.addAccount("Apple", "umair2", "tent");
        testPasswordManager.addAccount("Instagram", "umair2", "tent");

        testPasswordManager.removeAccount(1);
        assertEquals("Instagram", testPasswordManager.getWebsiteByIndex(1));
    }

    @Test
    void testPasswordSweep() {
        testPasswordManager.addAccount("Gmail", "umair", "tent");
        testPasswordManager.addAccount("Apple", "umair2", "mountain");
        testPasswordManager.addAccount("Instagram", "umair3", "forest");
        testPasswordManager.addAccount("Github", "umair4", "tent");
        testPasswordManager.addAccount("Snapchat", "umair5", "lake");
        testPasswordManager.addAccount("Steam", "umair6", "tent");

        testPasswordManager.passwordSweep();

        assertTrue(testPasswordManager.getRepeatPasswordByIndex(0));
        assertTrue(testPasswordManager.getRepeatPasswordByIndex(3));
        assertTrue(testPasswordManager.getRepeatPasswordByIndex(5));

        assertFalse(testPasswordManager.getRepeatPasswordByIndex(1));
        assertFalse(testPasswordManager.getRepeatPasswordByIndex(2));
        assertFalse(testPasswordManager.getRepeatPasswordByIndex(4));
    }

    @Test
    void testRandomPasswordGenerator() {
        int passwordLength = 10;
        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String RandomlyGeneratedPassword = testPasswordManager.generateRandomPassword(passwordLength);
            arr.add(RandomlyGeneratedPassword);
        }

        /* iteratively checks the value at index i with every other */
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                assertNotEquals(arr.get(i), arr.get(j));
            }
        }

    }


}