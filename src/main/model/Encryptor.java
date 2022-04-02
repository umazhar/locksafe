package model;

//creates Encryptor object to encrypt passwords
public final class Encryptor {

    //EFFECTS: creates encryptor object
    public Encryptor() {
    }

    //REQUIRES: password
    //MODIFIES: this
    //EFFECTS: encrypts password using simple ceasar cipher. Offset by key
    public static String encrypt(String passwordToEncrypt) {
        //converts string to character array in order to ease character offset
        char [] buffer = passwordToEncrypt.toCharArray();
        int key = 12;
        //iterating through each character and offsetting by key
        for (int i = 0; i < buffer.length; i++) {
            char letter = buffer[i];
            letter = (char) (letter + key); //offset each ASCII letter by key and cast into char
            buffer[i] = letter;
        }
        return new String(buffer);
    }

    //REQUIRES: password
    //MODIFIES: this
    //EFFECTS: decrypts password using simple ceasar cipher. Offset by key
    public static String decrypt(String passwordToEncrypt) {
        //converts string to character array in order to ease character offset
        char [] buffer = passwordToEncrypt.toCharArray();
        int key = 12;
        //iterating through each character and offsetting by key
        for (int i = 0; i < buffer.length; i++) {
            char letter = buffer[i];
            letter = (char) (letter - key); //offset each ASCII letter by key and cast into char
            buffer[i] = letter;
        }
        return new String(buffer);
    }
}
