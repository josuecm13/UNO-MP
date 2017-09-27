package com.uno.server.server;

import com.uno.cards.AbsCard;
import com.uno.cards.CardFactory;
import com.uno.interfaces.IServer;
import com.uno.server.players.Player;

import javax.activity.InvalidActivityException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class Server extends UnicastRemoteObject implements IServer, Serializable{

    private ArrayList<Player> players;
    private Map<Integer, ArrayList<AbsCard>> playerCards;
    private AbsCard topCard;
    private static final long serialVersionUID = 1L;
    private int playerID = new Random().nextInt();

    protected Server() throws RemoteException {
        players = new ArrayList<>();
        playerCards = new HashMap<>();
    }

    //ICard
    @Override
    public AbsCard generateCard(int clientID) throws Exception {
        AbsCard card = CardFactory.getCard();
        addToDraw(card,clientID);
        return card;
    }

    @Override
    public AbsCard getCard() throws RemoteException {
        return CardFactory.getCard();
    }

    @Override
    public boolean validateMove(AbsCard card)throws RemoteException {
        if(card.isWild())
            return true;
        return validateColor(card) || validateNumber(card);
    }

    @Override
    public boolean validateColor(AbsCard card) throws RemoteException {
        return card.getColor() == topCard.getColor();
    }

    @Override
    public boolean validateNumber(AbsCard card) throws RemoteException {
        return card.getNumber() == topCard.getNumber();
    }

    @Override
    public AbsCard pushCard(AbsCard card) throws RemoteException {
        if(validateMove(card)) {
            topCard = card;
            return null;
        }
        else{
            return card;
        }
    }

    //IServer
    @Override
    public String getPlayers() throws RemoteException {
        String playerString = "";
        for (Player p: players) {
            playerString += p.getPlayer() +"\n";
        }
        return playerString;
    }

    @Override
    public int addPlayer(String ip, String username, String password) throws RemoteException {
        Player player = new Player(username,ip);
        players.add(player);
        ++playerID;
        playerCards.put(playerID, new ArrayList<>());
        return playerID;
    }

    private void addToDraw(AbsCard card, int clientID) throws RemoteException{
        if(!playerCards.containsKey(clientID)) {
            throw new RemoteException("No existe el usuario");
        }
        ArrayList<AbsCard> draw = playerCards.get(clientID);
        draw.add(card);
        playerCards.replace(clientID,draw);
    }

    @Override
    public String getDraw(int clientID) throws RemoteException {
        if(!playerCards.containsKey(clientID)) {
            throw new RemoteException();
        }
        String result = "";
        ArrayList<AbsCard> draw = playerCards.get(clientID);
        for (AbsCard card: draw) {
            result += "Numero " + card.getNumber() +"\n"
                    + "Color " + card.getColor() +"\n\n";
        }
        return result;
    }

    @Override
    public AbsCard getTopCard() throws RemoteException {
        return topCard;
    }


}
