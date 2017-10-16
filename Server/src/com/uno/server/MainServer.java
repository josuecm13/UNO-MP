package com.uno.server;

import com.uno.interfaces.Gui;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class MainServer{


    private MainServer() throws MalformedURLException, RemoteException {
        Controller controller = new Controller();
        int opt;
        do{
            opt = Gui.menu("SERVER UNO",new String[]{"Obtener Jugadores","Iniciar Juego","Terminar Juego","Salir"});
            switch (opt){
                case 0:
                    System.out.println(controller.getPlayers());
                    break;
                case 1:
                    controller.startGame();
                    break;
                case 2:
                    controller.endGame();
                    break;
                default:
                    break;
            }
        }while (opt != 3);
        controller.endGame();
        System.exit(0);
    }


    public static void main(String[] args) throws MalformedURLException, RemoteException {
        new MainServer();
        return;
    }

}
