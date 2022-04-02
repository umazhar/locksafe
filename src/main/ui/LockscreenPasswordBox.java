package ui;

import model.PasswordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Arrays;
import model.*;


//Represents the password box of the login screen
public class LockscreenPasswordBox extends JPanel implements ActionListener {
    JButton button;
    JPasswordField passwordField;
    JLabel passwordCorrectnessLabel;
    JProgressBar bar;
    JFrame classFrame;

    ui.PasswordAppTable table = new ui.PasswordAppTable();

    // MODIFIES: this
    // EFFECTS: sets up components of login screen

    LockscreenPasswordBox(JFrame frame) {
        frame.setLayout(new FlowLayout());
        button = new JButton("Submit");
        button.addActionListener(this);
        passwordCorrectnessLabel = new JLabel("");
        passwordCorrectnessLabel.setForeground(Color.red);

        //password box
        passwordField = new JPasswordField(20);
        passwordField.addActionListener(this);

        passwordField.setPreferredSize(new Dimension(250, 40));

        //adding components to frame
        frame.add(passwordField);
        frame.add(button);
        frame.add(passwordCorrectnessLabel);
        classFrame = frame;
    }

    // MODIFIES: this
    // EFFECTS: What happens once login button is pressed. Checks if password is correct or incorrect
    //          if correct, open and create new password manager window.
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        bar = new JProgressBar();

        char[] input = passwordField.getPassword();
        if (isPasswordCorrect(input)) {
            passwordCorrectnessLabel.setText("Correct password.");
            System.out.println("Correct password!");

            classFrame.dispose();

            //setting up new Window for password manager app
            JFrame frame = new JFrame("Password Manager");

            //Adding table and menu bar to password manager app
            frame.getContentPane().add(table.getComponent());
            frame.setJMenuBar(table.getJMenuBar());

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setSize(600,300);
            frame.setVisible(true);

            testMethod(frame);

        } else {
            System.out.println("Incorrect password!");
            passwordCorrectnessLabel.setText("Incorrect password! Please try again!");

        }

        //Zero out the possible password, for security.
        Arrays.fill(input, '0');
        passwordField.selectAll();
        resetFocus();
    }

    public void testMethod(Frame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                PasswordManager ps = new PasswordManager();
                ps.printEventList(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // EFFECTS: checks if password correct
    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect;
        char[] correctPassword = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(input, correctPassword);
        }
        Arrays.fill(correctPassword,'0');

        return isCorrect;
    }

    //Must be called from the event dispatch thread.
    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }

}

