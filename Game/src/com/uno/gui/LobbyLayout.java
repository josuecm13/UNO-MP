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
    private JTable table;
    private Font gameFont;
    private JScrollPane pane;
    private JButton updateButton;
    private JButton startGameButton;
    private Controller controller;

    public LobbyLayout(String username) throws Exception {
        this.controller = Controller.getInstance();
        controller.setUsername(username);
        JFrame frame = initFrame("Lobby");
        setComponents(frame);
        frame.getContentPane().setBackground(Color.decode("#CC0000"));
        frame.pack();
        controller.setLobby(this);
        frame.setVisible(true);
    }

    public void addRowToTable() throws RemoteException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        deleteAllRows(model);
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
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setBackground(Color.decode("#CC0000"));
        table.setForeground(Color.yellow);
        gameFont = new Font("Lucida Sans",1,22);
        table.setFont(gameFont);
        table.setRowHeight(30);
        table.setShowVerticalLines(false);
        pane = new JScrollPane(table);
        pane.getViewport().setBackground(Color.decode("#CC0000"));
        pane.setBounds(10, 10, 880, 700);
    }

    private void setUpdateButton(){
        updateButton = new JButton("Update");
        updateButton.setFont(gameFont);
        updateButton.setBorderPainted(false);
        updateButton.setBounds(950, 220, 180, 60);
        updateButton.addActionListener(e ->{
            try {
                addRowToTable();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void setStartGameButton(){
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(gameFont);
        startGameButton.setBorderPainted(false);
        startGameButton.setBounds(950, 100, 180, 60);
        startGameButton.addActionListener(e ->{
            try {
                controller.startGame();
            } catch (Exception e1) {

            }
        });
    }

    public void startGame() throws Exception {
        new MainLayout();
    }

    public void deleteAllRows(DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }

    @Override
    protected void setComponents(JFrame window) throws Exception {
        window.setLayout(null);
        setTable();
        setUpdateButton();
        setStartGameButton();
        window.add(pane);
        window.add(updateButton);
        window.add(startGameButton);
        addRowToTable();
    }

}
