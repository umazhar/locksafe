package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.PasswordManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//testing Json reader
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PasswordManager pm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyManager.json");
        try {
            PasswordManager pm = reader.read();
            //assertEquals(0, pm.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        try {
            PasswordManager pm;
            JsonReader reader = new JsonReader("./data/testReaderManager.json");

            pm = reader.read();
            List<Account> accounts = pm.getAccounts();
            assertEquals(2, accounts.size());
            checkAccount("test1", "test2", "test3", accounts.get(0));
            checkAccount("test4", "test5", "test6", accounts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
