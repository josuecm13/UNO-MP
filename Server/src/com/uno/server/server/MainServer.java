package com.uno.server.server;

import com.uno.cards.AbsCard;
import com.uno.gui.MainLayout;
import com.uno.interfaces.Gui;
import com.uno.interfaces.IServer;
import com.uno.interfaces.Observer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class MainServer{ // implements Observer{

    IServer server;

    private MainServer() throws MalformedURLException, RemoteException {
        Utils.setCodeBase(IServer.class);
        this.server = new Server();
        Naming.rebind("UNOServer", server);
        //server.addObserver(this);
        int opt;
        do{
            opt = Gui.menu("SERVER UNO",new String[]{"Get Players","Salir"});
            switch (opt){
                case 0:
                    System.out.println(server.getPlayers());
                    break;
            }
        }while (opt != 1);
    }

    //@Override
    public void update() throws RemoteException {
        System.out.println(server.getTopCard().getNumber());
    }

    //@Override
    public int getID() throws RemoteException {
        return 0;
    }

    //@Override
    public void drawCards(int i) throws RemoteException {
        System.out.println("jeje");
    }

    //@Override
    public void chooseColor() throws RemoteException {

    }

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        new MainServer();
    }

}
