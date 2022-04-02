package persistence;

import model.*;
import org.json.*;
import model.PasswordManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads password Account from Json data

public class JsonReader {
    private String source;
    private static final String defaultDestination = "./src/resources/data/taskList.json";
    Encryptor encryptor = new Encryptor();
    PasswordManager pm = new PasswordManager();

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads password manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PasswordManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePasswordManager(jsonObject);
    }

    private PasswordManager parsePasswordManager(JSONObject jsonObject) {
        //String name = jsonObject.getString("user");
        addAccounts(pm, jsonObject);
        return pm;
    }

    // MODIFIES: pm
    // EFFECTS: parses accounts from JSON object and adds them to account manager
    private void addAccounts(PasswordManager pm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAcc = (JSONObject) json;
            addSingleAccount(pm, nextAcc);
        }
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // MODIFIES: wr
    // EFFECTS: parses from JSON object and adds it to password manager
    private void addSingleAccount(PasswordManager pm, JSONObject jsonObject) {
        String website = jsonObject.getString("website");
        String username = encryptor.decrypt(jsonObject.getString("username"));
        String password = encryptor.decrypt(jsonObject.getString("password"));

        pm.addAccount(website, username, password);
    }
}


