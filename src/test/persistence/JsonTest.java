package persistence;

import model.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;

//tests json object
public class JsonTest {
    protected void checkAccount(String website, String username, String password, Account account) {
        assertEquals(website, account.getWebsite());
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }

}
