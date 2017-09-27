package com.uno.server.server;

import com.uno.interfaces.Gui;
import com.uno.interfaces.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class MainServer {

    private MainServer() {
        try {
            IServer i =   new Server();
            //Naming.rebind("rmi//localhost//server", i);
            System.out.println("Server creado");
        } catch (Exception e) {
            System.out.println("Error Server");
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException {
        Utils.setCodeBase(IServer.class);

        IServer server = new Server();
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

}
