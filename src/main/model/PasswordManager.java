package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//representation of password manager along with its descriptors
public class PasswordManager implements Writable  {

    private ArrayList<Account> accountList;

    //create accountList object
    public PasswordManager() {
        accountList = new ArrayList<>();
    }

    //EFFECTS: Prints out eventLog list
    public void printEventList(EventLog el) {
        System.out.println("----------EVENT LOG START----------");
        for (Event next : el) {
            System.out.println(next.toString());
        }
        System.out.println("-----------EVENT LOG END-----------");
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: nothing
    public void addAccount(String website, String username, String password) {
        this.accountList.add(new Account(website, username, password));

        Event event = new Event("Added " + website + " account with username " + username + ".");
        EventLog.getInstance().logEvent(event);
    }

    //REQUIRES: index>=0
    //EFFECTS: removes an account from the password manager
    public void removeAccount(int index) {
        Event event = new Event("Removed " + accountList.get(index).getWebsite()
                                + " account with username " + accountList.get(index).getUsername() + ".");
        EventLog.getInstance().logEvent(event);

        accountList.remove(index);
    }

    //REQUIRES: accountIndex>=0
    //MODIFIES: this
    //EFFECTS: changes the account attributes depending on the parameters
    public void changeAccountAttribute(int accountIndex, String attributeType, String entry) {
        String attributeTypeLower = attributeType.toLowerCase();
        switch (attributeTypeLower) {
            case "website":
                accountList.get(accountIndex).setWebsite(entry);
                return;
            case "username":
                accountList.get(accountIndex).setUsername(entry);
                return;
            case "password":
                accountList.get(accountIndex).setPassword(entry);
                return;
            default:
        }
    }

    //MODIFIES: this
    //EFFECTS: Checks every password field with one another and changes the repeat flag of an account
    // to false if a password repeats
    public void passwordSweep() {
        for (int i = 0; i < accountList.size(); i++) {
            for (int j = i + 1; j < accountList.size(); j++) {
                if (accountList.get(i).getPassword().equals(accountList.get(j).getPassword())) {
                    accountList.get(i).setRepeatPassword();
                    accountList.get(j).setRepeatPassword();
                }
            }
        }
    }

    public int getSize() {
        return accountList.size();
    }

    public String getWebsiteByIndex(int index) {
        return accountList.get(index).getWebsite();
    }

    public String getUsernameByIndex(int index) {
        return accountList.get(index).getUsername();
    }

    public String getPasswordByIndex(int index) {
        return accountList.get(index).getPassword();
    }

    //REQUIRES: Index>=0
    //MODIFIES: this
    //EFFECTS: returns repeat password status based on index
    public boolean getRepeatPasswordByIndex(int index) {
        return accountList.get(index).getRepeatPasswordStatus();
    }

    //REQUIRES: length>=1
    //EFFECTS: randomly generates a password depending on a user chosen length
    public String generateRandomPassword(int length) {
        Random random = new Random(); //random object
        StringBuilder password = new StringBuilder(); //build password object
        //list of possible chars to use in password
        String possibleChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

        for (int i = 0; i < length; i++) { //iterate for each char in password
            int randomIndex = random.nextInt(possibleChars.length()); //pick random char for password
            password.append(possibleChars.charAt(randomIndex)); //append random char to string builder
        }
        Event event = new Event("Generated new password: " + password);
        EventLog.getInstance().logEvent(event);

        return password.toString();
    }

    //@Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accounts", accountsToJson());

        return json;
    }

    // EFFECTS: returns as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a : accountList) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of accounts
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accountList);
    }

}
