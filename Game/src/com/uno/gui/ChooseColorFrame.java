package com.uno.gui;

import com.uno.client.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;



public class ChooseColorFrame extends GameView{
    private JButton btnRed;
    private JPanel panel1;
    private JButton btnGreen;
    private JButton btnYellow;
    private JButton bntBlue;
    private JButton OKButton;
    private Controller c;


    public ChooseColorFrame(Controller controller) throws Exception {

        c = controller;
        setComponents(this);

        btnRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.setSelectedColor(0);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.setSelectedColor(1);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnYellow.addActionListener(e -> {
            try {
                c.setSelectedColor(2);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        bntBlue.addActionListener(e -> {
            try {
                c.setSelectedColor(3);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        setVisible(true);
        OKButton.addActionListener(actionEvent -> {
            dispose();
        });
    }

    @Override
    protected void setComponents(JFrame window) throws Exception {
        window.setSize(600,300);
        panel1.add(btnRed);
        panel1.add(btnGreen);
        panel1.add(btnYellow);
        panel1.add(bntBlue);
        window.add(panel1);
    }
}
