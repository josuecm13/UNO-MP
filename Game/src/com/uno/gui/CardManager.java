package com.uno.gui;

import com.uno.cards.AbsCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
public class CardManager extends JComponent implements MouseListener {

    private static final Color BACKGROUND_COLOR = Color.decode("#CC0000");

    private static final String[] cards = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static final String[] colors = {"_red", "_green", "_yellow", "_blue", ""};

    private ArrayList<CardGUI> deck;
    private CardGUI currentCard = null;
    private CardGUI card;
    private MainLayout layout;

    public CardManager(ArrayList<CardGUI> cards, MainLayout layout) {
        deck = cards;
        this.layout = layout;
        addMouseListener(this);
    }

    public void processCard() throws Exception {
        if (currentCard != null) {
            int index = deck.indexOf(currentCard);
            this.card = deck.remove(index);
            CardGUI cardGUITemp = getCard();
            AbsCard card = cardGUITemp.getCard();
            AbsCard newCar = layout.sendCard(card);
            if(newCar == null) {
                layout.setTopCard();
            } else {
                deck.add(cardGUITemp);
            }
            placeDeck(deck);
            repaint();
        }
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

    public static boolean movePosible(AbsCard card, AbsCard dropedCard) {
        if(card.getColor() == dropedCard.getColor()) {
            return true;
        } else if(card.getNumber() == dropedCard.getNumber()) {
            return true;
        } else if(dropedCard.getColor() == 4){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, width, height);
        //ard.draw(g, this);
        for (CardGUI c : deck) {
            c.draw(g, this);
        }
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
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println("carta");
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
