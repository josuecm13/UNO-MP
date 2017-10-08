package com.uno.gui;

import com.uno.client.Controller;
import com.uno.players.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Admin on 7/10/2017.
 */
public class LobbyLayout extends GameView {

    private ImageIcon logoImg = new ImageIcon("res/uno_icon.png");
    JTable table;
    JScrollPane pane;
    Controller controller;

    public LobbyLayout(String username) throws Exception {
        this.controller = Controller.getInstance();
        controller.setUsername(username);
        JFrame frame = initFrame("Lobby");
        setComponents(frame);
        frame.getContentPane().setBackground(Color.decode("#CC0000"));
        frame.pack();
        frame.setVisible(true);
    }

    public void addRowToTable() throws RemoteException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<Player> players = controller.getPlayers();
        Object rowInfo[] = new Object[2];
        for (int i = 0; i < players.size(); i++) {
            rowInfo[0] = players.get(i).getUser();
            rowInfo[1] = players.get(i).getIp();
            model.addRow(rowInfo);
        }
    }


    private void setTable(){
        table = new JTable();
        Object[] columns = {"Usernames", "IP"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setBackground(Color.decode("#CC0000"));
        table.setForeground(Color.yellow);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);
        pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 700);
    }

    @Override
    protected void setComponents(JFrame window) throws Exception {
        window.setLayout(null);
        setTable();
        window.add(pane);
        addRowToTable();
    }
}
