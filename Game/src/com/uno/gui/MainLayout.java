package com.uno.gui;


import com.uno.cards.AbsCard;
import com.uno.client.Controller;
import com.uno.players.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static com.uno.gui.CardManager.placeDeck;


public class MainLayout extends GameView implements ActionListener{

    private Controller controller;
    private CardManager cardManager;
    private ArrayList<CardGUI> hand = new ArrayList<>();

    private JLabel lbl = new JLabel();
    private JButton drawButton;
    private JButton btn;
    private JButton passBtn;
    private JTextField chatField;
    private JTextArea chatArea;
    private JScrollPane scroll;
    private JScrollPane textScroll;
    private JTable table;

    private ImageIcon backCard = new ImageIcon("res/back.png");
    private ImageIcon UNObtn = new ImageIcon("res/botnUNO.png");
    private ImageIcon orientImgInv = new ImageIcon("res/rotateImgInv.png");
    private ImageIcon orientImg = new ImageIcon("res/rotateImg.png");
    private ImageIcon passImg = new ImageIcon("res/pass.png");


    //============================================================== constructor
    public MainLayout() throws Exception{
        controller = Controller.getInstance();
        generateDeck(7);
        this.cardManager = new CardManager(hand);
        JFrame window = initFrame("UNO");
        cardManager.setLayout(this);
        setComponents(window);
        controller.setObserver(this);
        window.pack();
        window.setVisible(true);
        Thread thread = new Thread(new GameUpdater(this));
        thread.start();
    }

    private void generateDeck(int cardNumb) throws Exception {
         for (int face=0; face < cardNumb; face++) {
            AbsCard card = controller.getCard();
            ImageIcon img = generateCardIcon(card);
            CardGUI cardGUI = new CardGUI(img, card);
            hand.add(cardGUI);
        }
        placeDeck(hand);
    }

    public void drawCard(JFrame frame) throws Exception {
        AbsCard card = controller.drawCard();
        if(card != null) {
            ImageIcon img = generateCardIcon(card);
            CardGUI cardGUI = new CardGUI(img, card);
            hand.add(cardGUI);
            placeDeck(hand);
            frame.repaint();
        }
    }

    private void setDrawButton(JFrame frame) {
        drawButton = new JButton("");
        ImageIcon img = new ImageIcon(getScaledImage(backCard.getImage(), 177, 249));
        setButtonProps(drawButton, img);
        drawButton.setBorder( new EmptyBorder( 16, 90, 16, 16 ) );
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    drawCard(frame);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println(hand.size());
            }
        });
    }

    private void setUNOButton(JFrame frame) {
        btn = new JButton("");
        ImageIcon img = new ImageIcon(getScaledImage(UNObtn.getImage(), 249, 249));
        setButtonProps(btn, img);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(hand.size() == 1)
                    JOptionPane.showMessageDialog(instance , "UNO",
                            "UNO", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void setPassButton(JFrame frame) {
        passBtn = new JButton("");
        ImageIcon img = new ImageIcon(getScaledImage(passImg.getImage(), 200, 200));
        setButtonProps(passBtn, img);
        passBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setChat() {

        Font font = new Font("Courier", Font.BOLD,16);

        chatField = new JTextField(4);
        chatField.addActionListener(this);
        chatField.setFont(font);

        chatArea = new JTextArea(1,2);
        chatArea.setEditable(false);
        chatArea.setFont(font);

        textScroll = new JScrollPane(chatArea);


        table = new JTable();
        Object[] columns = {"Usernames", "Cards", "Turn"};
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
        Font gameFont = new Font("Lucida Sans",1,16);
        table.setFont(gameFont);
        table.setRowHeight(16);
        table.setShowVerticalLines(false);

        scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.decode("#CC0000"));
        scroll.setPreferredSize(new Dimension(40, 10)); // No se lo que hice pero medio funciona. El tamaño no se cambia :v


    }


    public AbsCard sendCard(AbsCard card) throws RemoteException {
        return controller.pushCard(card);
    }

    public void setTopCard() throws Exception {
        AbsCard card = controller.setTopCard();
        ImageIcon img = generateCardIcon(card);
        lbl.setIcon(img);
    }

    @Override
    protected void setComponents(JFrame window) throws Exception {
        window.setContentPane(cardManager);
        window.setLayout(new GridBagLayout());
        setDrawButton(window);
        setUNOButton(window);
        setPassButton(window);
        setChat();
        setTopCard();

        GridBagConstraints gbc = new GridBagConstraints();


        gbc.weightx = gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        window.add(chatField, gbc);

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        window.add(textScroll, gbc);

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        window.add(scroll, gbc);

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = gbc.weighty = 0.0;
        gbc.insets = new Insets(0,0,200, 0);
        gbc.weightx=1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        window.add(passBtn, gbc);

        gbc.weightx = gbc.weighty = 1.0;
        gbc.insets = new Insets(0,0,200, 0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        window.add(drawButton, gbc);

        gbc.weightx = gbc.weighty = 1.0;
        gbc.insets = new Insets(0,0,200, 0);
        gbc.gridx = 2;
        gbc.gridy = 1;
        window.add(btn, gbc);

        gbc.weightx = gbc.weighty = 1.0;
        gbc.insets = new Insets(0,0,200, 0);
        gbc.gridx = 3;
        gbc.gridy = 1;
        window.add(lbl, gbc);
    }

    public void addRowToTable() throws RemoteException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        deleteAllRows(model);
        ArrayList<Player> players = controller.getPlayers();

        Object rowInfo[] = new Object[3];
        for (int i = 0; i < players.size(); i++) {
            rowInfo[0] = players.get(i).getUser();
            rowInfo[1] = players.get(i).getDeck().size();
            if(players.get(i).getTurn()){
                rowInfo[2] = "X";
            }else{
                rowInfo[2] = "";
            }
            model.addRow(rowInfo);
        }
    }

    public void setText() throws RemoteException {
        chatArea.setText(controller.setMessage());
        chatArea.setCaretPosition(chatArea.getDocument().getLength());

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String text = chatField.getText();
        try {
            controller.sendMessage(controller.getUser() + ": " + text + "\n");
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        chatField.setText("");
    }


    private class GameUpdater implements Runnable{

        MainLayout view;

        GameUpdater(MainLayout view){
            this.view = view;
        }

        @Override
        public void run() {
            while(true){
                try {
                    view.setTopCard();
                    addRowToTable();
                    setText();
                } catch (Exception e) {
                    //ignore
                }
                try {
                    Thread.sleep(1*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
