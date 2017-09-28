package com.uno.client;

import com.uno.cards.AbsCard;
import com.uno.gui.ChooseColorFrame;
import com.uno.gui.MainLayout;
import com.uno.interfaces.IServer;
import com.uno.interfaces.Observer;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class Controller implements Serializable, Observer {

    private static String user;
    private int clientID;
    IServer server;
    MainLayout view;

    public Controller() throws Exception{
        this.server = (IServer) Naming.lookup("//localhost/UNOServer");
    }

    public void setSelectedColor(int color) throws RemoteException {
        server.setSelectedColor(color);
    }

    public void setUsername(String username) throws Exception{
        user = username;
        this.clientID =  server.addPlayer(InetAddress.getLocalHost().getHostAddress().toString(),username, "0");
    }

    public void setObserver (MainLayout view) throws RemoteException {
        this.view = view;
        server.addObserver(this);
    }

    public AbsCard getCard() throws Exception {
        return server.getCard();
    }

    public AbsCard getFirstCard() throws Exception {
        AbsCard card = server.getCard();
        while (card.isSpecial()) {
            card = server.getCard();
        }
        return card;
    }

    public AbsCard getTopCard() throws RemoteException {
        return server.getTopCard();
    }

    public AbsCard setTopCard() throws Exception {
        AbsCard card;
        if(getTopCard() == null) {
            card = getFirstCard();
        } else {
            card = getTopCard();
        }
        System.out.println(card.getNumber() + " pene " + card.getColor());
        return card;
    }

    public AbsCard drawCard() throws Exception {
        return server.generateCard(clientID);
    }

    public AbsCard pushCard(AbsCard card) throws RemoteException {
        return server.pushHelper(card, clientID);
    }

    @Override
    public void update() throws RemoteException {
        try {
            view.setTopCard();
            SwingUtilities.updateComponentTreeUI(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getID() throws RemoteException{
        return this.clientID;
    }

    @Override
    public void drawCards(int n) throws RemoteException {
        for (int i = 0; i < n; i++) {
            try {
                view.drawCard(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void chooseColor() throws RemoteException {
        try {
            ChooseColorFrame frame = new ChooseColorFrame(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
