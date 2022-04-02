package ui;

import model.PasswordManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

//Represents UI of password app manager (table and buttons)
public class PasswordAppTable extends JPanel {
    public static final String[] columns = {
            "Website", "Account", "Password",//"Security"
    };
    private static final String JSON_STORE = "./data/accountinfo.json";
    private final DefaultTableModel model = new DefaultTableModel(columns, 0);
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    PasswordManager passwordManagerModel = new PasswordManager();
    JMenuBar menuBar = new JMenuBar();
    JButton addButton = new JButton("Add");
    JButton clearButton = new JButton("Delete");
    JTextField enteredWebsite = new JTextField(10);
    GhostText ghostText = new GhostText(enteredWebsite, "Website Name");
    JTextField enteredUsername = new JTextField(10);
    GhostText ghostTextUsername = new GhostText(enteredUsername, "Username");
    JTextField enteredPassword = new JTextField(10);
    GhostText ghostTextPassword = new GhostText(enteredPassword, "Password");
    JPanel buttonPane = new JPanel();
    JMenu fileMenu = new JMenu("File");
    JMenu aboutMenu = new JMenu("About");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem generatePasswordItem = new JMenuItem("Generate password");
    JMenuItem exitItem = new JMenuItem("Exit");
    JTable table = new JTable(model);
    JsonReader reader = new JsonReader(JSON_STORE);
    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);

    //Constructor which sets up buttons, menubar and text boxes
    PasswordAppTable() {
        //Add items to button panel
        addItemsToButtonPane();

        //Add panels and table to the main panel
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPane, BorderLayout.SOUTH);

        //Load item listener
        loadItem.addActionListener(f -> readFromFile());

        //Save item action listener
        saveItem.addActionListener(g -> saveToFile());

        //Password generator action listener
        generatePasswordItem.addActionListener(g -> generatePasswordWindow());

        //Action listener for exit button
        exitItem.addActionListener(h ->
                System.exit(0));

        //add items to menubar
        addItemsToMenuBar();

        // This code is called when the Add button is clicked.
        addButton.addActionListener(e -> addTextBoxes());

        // This code is called when the Clear button is clicked.
        clearButton.addActionListener(e -> {
            //Clear the form
            if (table.getSelectedRow() != -1) {
                passwordManagerModel.removeAccount(table.getSelectedRow());
                model.removeRow(table.getSelectedRow());
            }
        });

    }

    //Based off of https://github.com/mitchtabian/JavaTutorial10/blob/master/SignUpForm.java
    //EFFECTS: creates password generator window
    private void generatePasswordWindow() {
        String generatedPassword = passwordManagerModel.generateRandomPassword(10);
        JFrame passwordFrame = new JFrame("Generated Password");

        passwordFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        passwordFrame.setBounds(200, 100, 380, 140);

        Container container = passwordFrame.getContentPane();
        container.setLayout(null);

        JLabel passwordLabel = new JLabel("Your randomly generated password is ");
        passwordLabel.setBounds(60,5,250,30);

        JTextField nameText = new JTextField(generatedPassword);
        nameText.setBounds(65,30,250,30);

        container.add(passwordLabel);
        container.add(nameText);
        passwordFrame.setVisible(true);
    }

    //EFFECTS: adds buttons to the panel
    private void addItemsToButtonPane() {
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(clearButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(enteredWebsite);
        buttonPane.add(enteredUsername);
        buttonPane.add(enteredPassword);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
    }

    //EFFECTS: adds items to menu bar
    private void addItemsToMenuBar() {
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(generatePasswordItem);
        fileMenu.add(exitItem);


        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
    }

    //EFFECTS: adds text boxes
    private void addTextBoxes() {
        passwordManagerModel.addAccount(enteredWebsite.getText(),
                enteredUsername.getText(),
                enteredPassword.getText());
        model.addRow(new Object[]{enteredWebsite.getText(), enteredUsername.getText(), enteredPassword.getText()});
        //setSecurity();

        //debugging account adding
        for (int i = 0; i < passwordManagerModel.getSize(); i++) {
            System.out.println("Account #" + (i + 1) + " " + passwordManagerModel.getWebsiteByIndex(i));
        }
        enteredWebsite.setText("");
        enteredUsername.setText("");
        enteredPassword.setText("");
        GhostText ghostTextWebsite1 = new GhostText(enteredWebsite, "Website Name");
        GhostText ghostTextUsername1 = new GhostText(enteredUsername, "Username");
        GhostText ghostTextPassword1 = new GhostText(enteredPassword, "Password");

    }

    //EFFECTS: saves to JSON file
    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(passwordManagerModel);
            jsonWriter.close();
            System.out.println("Saved account information to " + JSON_STORE);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    //EFFECTS: reads from JSON file and populates table
    public void readFromFile() {
        //Load item Listener
        try {
            while (model.getRowCount() != 0) {
                model.removeRow(0);
                passwordManagerModel.removeAccount(0);
            }

            passwordManagerModel = reader.read();
            System.out.println("Loaded account information from " + JSON_STORE);
            passwordManagerModel.passwordSweep();

            //setSecurity();
            for (int i = 0; i < passwordManagerModel.getSize(); i++) {
                model.addRow(new Object[]{passwordManagerModel.getWebsiteByIndex(i),
                        passwordManagerModel.getUsernameByIndex(i),
                        passwordManagerModel.getPasswordByIndex(i)});
            }

        } catch (IOException ex) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: return the main panel
    public JComponent getComponent() {
        return mainPanel;
    }

    //EFFECTS: return Jmenu bar
    public JMenuBar getJMenuBar() {
        return menuBar;
    }

    //EFFECTS: returns instance of password manager
    public PasswordManager returnPasswordManager() {
        return passwordManagerModel;
    }

    //EFFECTS: sets security status on table
    public void setSecurity() {
        String secure = "Secure!";
        String notSecure = "Repeated password!";

        for (int i = 0; i < passwordManagerModel.getSize(); i++) {
            if (passwordManagerModel.getRepeatPasswordByIndex(i)) {
                model.setValueAt(notSecure, i, 3);
            } else {
                model.setValueAt(secure, i, 3);

            }
        }

    }

    // EFFECTS: Adds ghost text to text boxes
    //taken from https://stackoverflow.com/questions/10506789/how-to-display-faint-gray-ghost-text-in-a-jtextfield
    public static class GhostText implements FocusListener, DocumentListener, PropertyChangeListener {
        private final JTextField textfield;
        private boolean isEmpty;
        private Color ghostColor;
        private Color foregroundColor;
        private final String ghostText;

        protected GhostText(final JTextField textfield, String ghostText) {
            super();
            this.textfield = textfield;
            this.ghostText = ghostText;
            this.ghostColor = Color.LIGHT_GRAY;
            textfield.addFocusListener(this);
            registerListeners();
            updateState();
            if (!this.textfield.hasFocus()) {
                focusLost(null);
            }
        }

        public void delete() {
            unregisterListeners();
            textfield.removeFocusListener(this);
        }

        private void registerListeners() {
            textfield.getDocument().addDocumentListener(this);
            textfield.addPropertyChangeListener("foreground", this);
        }

        private void unregisterListeners() {
            textfield.getDocument().removeDocumentListener(this);
            textfield.removePropertyChangeListener("foreground", this);
        }

        public Color getGhostColor() {
            return ghostColor;
        }

        public void setGhostColor(Color ghostColor) {
            this.ghostColor = ghostColor;
        }

        private void updateState() {
            isEmpty = textfield.getText().length() == 0;
            foregroundColor = textfield.getForeground();
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (isEmpty) {
                unregisterListeners();
                try {
                    textfield.setText("");
                    textfield.setForeground(foregroundColor);
                } finally {
                    registerListeners();
                }
            }

        }

        @Override
        public void focusLost(FocusEvent e) {
            if (isEmpty) {
                unregisterListeners();
                try {
                    textfield.setText(ghostText);
                    textfield.setForeground(ghostColor);
                } finally {
                    registerListeners();
                }
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateState();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateState();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateState();
        }

    }

}
