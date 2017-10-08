package com.uno.server;

import com.uno.interfaces.Gui;
import com.uno.interfaces.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class MainServer{

    IServer server;

    private MainServer() throws MalformedURLException, RemoteException {
        Utils.setCodeBase(IServer.class);
        this.server = new Server();
        Naming.rebind("UNOServer", server);

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

    private void createRegistry(int i,IServer server) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(7777);
        registry.rebind("UNOServer",server);
    }


    public static void main(String[] args) throws MalformedURLException, RemoteException {
        new MainServer();
    }

}
