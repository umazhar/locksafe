package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Creates Json writer class

// Parts of this code is based off of/copied from JsonSerializationDemo example
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            PasswordManager pm = new PasswordManager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            PasswordManager pm = new PasswordManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyManager.json");
            writer.open();
            writer.write(pm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyManager.json");
            pm = reader.read();
            assertEquals(0, pm.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PasswordManager pm = new PasswordManager();
            pm.addAccount("apple1", "apple2", "apple3");
            pm.addAccount("apple4", "apple5", "apple6");

            JsonWriter writer = new JsonWriter("./data/testWriterManagerWithAccounts.json");
            writer.open();
            writer.write(pm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterManagerWithAccounts.json");
            pm = reader.read();
            assertEquals(2, pm.getSize());
            List<Account> accounts = pm.getAccounts();
            assertEquals(2, accounts.size());
            checkAccount("apple1", "apple2", "apple3", accounts.get(0));
            checkAccount("apple4", "apple5", "apple6", accounts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}