package com.uno.server;


import com.uno.interfaces.IServer;
import com.uno.players.Player;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Controller {

    private IServer server;


    public Controller() throws RemoteException, MalformedURLException {
        System.setProperty("java.security.policy", "file:./java.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        createRegistry(new Server());
    }

    public  String getPlayers() throws RemoteException {
        ArrayList<Player> players = (ArrayList<Player>) server.getPlayers();
        String str = "";
        for (Player p : players) {
            str += p.getPlayer() + "\n";
        }
        return str;
    }

    private void createRegistry(IServer server) throws RemoteException, MalformedURLException {
        Registry registry = LocateRegistry.createRegistry(7777);
        this.server = server;
        registry.rebind("UNOServer",server);
    }

    public void startGame() throws RemoteException{
        //
    }

    public void endGame() throws RemoteException{
        server.writeMessage("***** El Servidor ha terminado el juego *****\n");
        System.out.println(server.getMessage());
        System.exit(0);
    }

}
