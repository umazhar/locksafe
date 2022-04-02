package model;

import org.json.JSONObject;

//representation of an account along with its descriptors
public class Account {
    private String website;
    private String username;
    private String password;
    private boolean repeatPassword;

    JSONObject json = new JSONObject();
    Encryptor encryptor = new Encryptor();

    //REQUIRES: website, username, password is nonzero
    //MODIFIES: nothing
    //EFFECTS: attributes associated with accounts are set
    public Account(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
        this.repeatPassword = false;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword() {
        this.repeatPassword = true;
    }

    public boolean getRepeatPasswordStatus() {
        return repeatPassword;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: converts strings to a json attribute and adds them to json object
    public JSONObject toJson() {
        String encryptedUsername = encryptor.encrypt(username);
        String encryptedPassword = encryptor.encrypt(password);

        json.put("website", website);
        json.put("username", encryptedUsername);
        json.put("password", encryptedPassword);
        return json;
    }
}
