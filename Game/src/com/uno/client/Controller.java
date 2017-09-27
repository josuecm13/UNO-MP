package com.uno.client;

import com.uno.cards.AbsCard;
import com.uno.interfaces.IServer;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class Controller implements Serializable{

    public static String user;
    public static int clientID;
    IServer server;

    public Controller() throws Exception{
        this.server = (IServer) Naming.lookup("//localhost/UNOServer");
    }

    public void setUsername(String username) throws Exception{
        user = username;
        clientID =  server.addPlayer(InetAddress.getLocalHost().getHostAddress().toString(),username, "0");
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
        return card;
    }

    public AbsCard drawCard() throws Exception {
        return server.generateCard(clientID);
    }

    public AbsCard pushCard(AbsCard card) throws RemoteException {
        return server.pushCard(card);
    }

    /*
    public static void main(String[] args) throws Exception {
        IServer server =(IServer) Naming.lookup("//localhost/UNOServer");
        int opt;
        user = Gui.input("log in", "ingrese un nombre de usuario:");
        clientID = server.addPlayer(InetAddress.getLocalHost().getHostAddress().toString(),user, "0");
    }
    */

}
