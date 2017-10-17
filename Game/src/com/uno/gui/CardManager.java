package com.uno.gui;

import com.uno.cards.AbsCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static com.uno.gui.GameView.generateCardIcon;

public class CardManager extends JComponent implements MouseListener, Serializable {

    private static final Color BACKGROUND_COLOR = Color.decode("#CC0000");

    private static final String[] cards = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static final String[] colors = {"_red", "_green", "_yellow", "_blue", ""};

    private ArrayList<CardGUI> deck;
    private CardGUI currentCard = null;
    private CardGUI card;
    private MainLayout layout;

    public CardManager(ArrayList<CardGUI> cards) {
        deck = cards;
        addMouseListener(this);
    }

    public void processCard() throws Exception {
        if (currentCard != null) {
            int index = deck.indexOf(currentCard);
            this.card = deck.remove(index);
            CardGUI cardGUITemp = getCard();
            AbsCard card = cardGUITemp.getCard();
            AbsCard newCar = layout.sendCard(card);
            if(newCar != null) {
                deck.add(cardGUITemp);
            }
            placeDeck(deck);
            repaint();
        }
    }

    public void setLayout(MainLayout layout){
        this.layout = layout;
    }

    public CardGUI getCard() {
        return card;
    }

    public static String setCardImage(AbsCard card) {
        String cardStr;
        int color = card.getColor();
        if(!card.isSpecial()) {
            cardStr = setImageNumber(card) + colors[color];
        } else {
            cardStr = card.getPower() + colors[color];
        }
        return "res/" + cardStr + ".png";
    }

    private static String setImageNumber(AbsCard card) {
        int number = card.getNumber();
        return cards[number];
    }

    public static void placeDeck(ArrayList<CardGUI> hand) {
        int initX = 656;
        int initY = 675;
        int cant = hand.size();
        if(cant > 15) {
            int xPos = initX - (cant * 22);
            for (CardGUI c : hand) {
                c.moveTo(xPos, initY);
                xPos += 45;
            }
        } else {
            int xPos = initX - (cant * 33);
            for (CardGUI c : hand) {
                c.moveTo(xPos, initY);
                xPos += 70;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, width, height);
        for (CardGUI c : deck) {
            c.draw(g, this);
        }
    }

    public boolean isEmpty() {
        return deck.size() == 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();   // Save the x coord of the click
        int y = e.getY();   // Save the y coord of the click
        for (int crd=deck.size()-1; crd>=0; crd--) {
            CardGUI testCard = deck.get(crd);
            if (testCard.contains(x, y)) {
                currentCard = testCard;
                try {
                    processCard();
                    if (isEmpty()) {
                        JOptionPane.showMessageDialog(this , "Felicidades ha ganado",
                                "Fin de la partida", JOptionPane.PLAIN_MESSAGE);{
                            System.exit(0);
                        }
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {
        currentCard = null;
    }

}
