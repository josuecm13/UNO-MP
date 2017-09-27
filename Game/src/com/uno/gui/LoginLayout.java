package com.uno.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ${gaboq} on 26/9/2017.
 */

public class LoginLayout extends GameView {

    private ImageIcon logoImg = new ImageIcon("res/uno_icon.png");
    private ImageIcon goImg = new ImageIcon("res/goButton.png");

    private JButton go;
    private JTextField nameField;    //textField.getText();
    private JLabel username;
    private JLabel logo;


    LoginLayout() {
        JFrame frame = initFrame("Login");
        setComponents(frame);
        frame.getContentPane().setBackground(Color.decode("#CC0000"));
        frame.pack();
        frame.setVisible(true);
    }


    private void setGoButton() {
        go = new JButton("");
        ImageIcon img = new ImageIcon(getScaledImage(goImg.getImage(), 249, 249));
        setButtonProps(go, img);
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(nameField.getText());
            }
        });
    }

    private void setUserLabel() {
        username = new JLabel("Username: ", SwingConstants.RIGHT);
        Font font = new Font("Courier", Font.BOLD,40);
        username.setFont(font);
        username.setForeground (Color.white);
    }

    private void setLogoLabel() {
        logo = new JLabel(logoImg);
    }

    private void setTextField() {
        nameField = new JTextField(15);
        nameField.setBackground(Color.decode("#CC0000"));
        Font font = new Font("Courier", Font.BOLD,40);
        nameField.setFont(font);
        nameField.setForeground (Color.white);
    }

    @Override
    protected void setComponents(JFrame window) {
        setGoButton();
        setLogoLabel();
        setTextField();
        setUserLabel();
        window.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        window.add(logo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        window.add(username, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 2;
        window.add(nameField, gbc);
        gbc.gridx = 5;
        gbc.gridy = 4;
        window.add(go, gbc);
    }

    public static void main(String[] args) {
        new LoginLayout();
    }
}
