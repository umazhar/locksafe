package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JFrame;

//represents UI elements of lockscreen
public class LockscreenUI extends JFrame {
    //constructor which sets up UI
    public LockscreenUI() {
        ImageIcon image = new ImageIcon("locksafe_logo.png");
        Image scaleImage = image.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT);
        image = new ImageIcon(scaleImage);

        JLabel label = new JLabel("Please enter password: ");
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setForeground(new Color(65, 193, 182));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(image);

        //Create and set up the window.
        JFrame frame = new JFrame();

        frame.setTitle("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(label);
        frame.add(iconLabel);
        frame.setSize(300, 450);
        frame.setVisible(true);

        //frame.getContentPane().setBackground(new Color(0x5c696b)); //sets bg colour to grey

        //password
        new LockscreenPasswordBox(frame);
        frame.setVisible(true);

    }
}



