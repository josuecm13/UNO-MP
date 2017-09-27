package com.uno.gui;


import com.uno.cards.AbsCard;
import com.uno.client.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static com.uno.gui.CardManager.placeDeck;


public class MainLayout extends GameView{

    private Controller controller;
    private CardManager cardManager;
    private ArrayList<CardGUI> hand = new ArrayList<>();

    private JLabel lbl;
    private JButton drawButton;
    private JButton btn;

    private ImageIcon backCard = new ImageIcon("res/back.png");
    private ImageIcon UNObtn = new ImageIcon("res/botnUNO.png");
    private ImageIcon orientImgInv = new ImageIcon("res/rotateImgInv.png");
    private ImageIcon orientImg = new ImageIcon("res/rotateImg.png");

    //============================================================== constructor
    public MainLayout() throws Exception{
        this.controller = new Controller();
        generateDeck(7);
        this.cardManager = new CardManager(hand, this);
        JFrame window = initFrame("UNO");
        setComponents(window);
        window.pack();
        window.setVisible(true);
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

    private void drawCard(JFrame frame) throws Exception {
        AbsCard card = controller.drawCard();
        ImageIcon img = generateCardIcon(card);
        CardGUI cardGUI = new CardGUI(img, card);
        hand.add(cardGUI);
        placeDeck(hand);
        frame.repaint();
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
                    System.exit(0);
            }
        });
    }

    public AbsCard sendCard(AbsCard card) throws RemoteException {
        return controller.pushCard(card);
    }


    public void setTopCard() throws Exception {
        AbsCard card = controller.setTopCard();
        ImageIcon img = generateCardIcon(card);
        lbl = new JLabel(img);
        lbl.setBorder(new EmptyBorder(16, 16, 16, 100));
    }

    @Override
    protected void setComponents(JFrame window) throws Exception {
        window.setContentPane(cardManager);
        setDrawButton(window);
        setUNOButton(window);
        setTopCard();
        window.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        window.add(drawButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        window.add(btn, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        window.add(lbl, gbc);
    }

}
