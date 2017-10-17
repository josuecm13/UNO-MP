package com.uno.server;

import com.uno.cards.AbsCard;
import com.uno.cards.CardFactory;
import com.uno.cards.special.DrawFour;
import com.uno.cards.special.DrawTwo;
import com.uno.cards.special.Reverse;
import com.uno.cards.special.Skip;
import com.uno.interfaces.IServer;
import com.uno.interfaces.Observer;
import com.uno.players.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements IServer, Serializable {

    private ArrayList<Player> players;
    private ArrayList<Observer> observers;
    private Map<Integer, Observer> clientObservers;
    private Map<Player, Integer> playersID;
    private Map<Integer, Player> idPlayers;
    private AbsCard topCard;
    private String message;
    private static final long serialVersionUID = 1L;
    private int playerID = new Random().nextInt();
    private int currentClient;
    private Player turnPlayer;
    private boolean isReady = false;

    Server() throws RemoteException {
        players = new ArrayList<>();
        message = "";
        //playerCards = new HashMap<>();
        observers = new ArrayList<>();
        playersID = new HashMap<>();
        idPlayers = new HashMap<>();
        clientObservers = new HashMap<>();
        topCard = getCard();
    }

    private void nextTurn(int turn) {
        int index = players.indexOf(turnPlayer);
        turnPlayer.setTurn(false);
        if(index + turn < players.size())
            turnPlayer = players.get(index + turn);
        else {
            index = (index + turn) % players.size();
            turnPlayer = players.get(index);
        }
        currentClient = playersID.get(turnPlayer);
        turnPlayer.setTurn(true);
    }

    private void endTurn() {
        turnPlayer.setTurn(false);
    }

    //ICard
    @Override
    public AbsCard generateCard(int clientID) throws Exception {
        AbsCard card = CardFactory.getCard();
        if(addToDraw(card,clientID)) {
            nextTurn(1);
            return card;
        }
        return null;
    }

    @Override
    public String getMessage() throws RemoteException {
        return message;
    }

    @Override
    public void writeMessage(String s) throws RemoteException {
        message += s;
    }


    @Override
    public Boolean canDraw() throws RemoteException {
        return isReady;
    }

    @Override
    public AbsCard getCard() throws RemoteException {
        return CardFactory.getCard();
    }

    @Override
    public boolean validateMove(AbsCard card)throws RemoteException {
        if(card.isWild()){
            notifyOb(currentClient);
            return true;
        }
        return validateColor(card) || validateNumber(card);
    }

    @Override
    public boolean validateColor(AbsCard card) throws RemoteException {
        if(topCard == null)
            return true;
        return card.getColor() == topCard.getColor();
    }

    @Override
    public boolean validateNumber(AbsCard card) throws RemoteException {
        if(topCard == null)
            return true;
        return card.getNumber() == topCard.getNumber();
    }

    @Override
    public AbsCard pushCard(AbsCard card) throws RemoteException {
        if(validateMove(card)) {
            topCard = card;
            idPlayers.get(currentClient).removeCard(card);
            card = null;
            try {
                applyPower(topCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    @Override
    public AbsCard pushHelper(AbsCard absCard, int id) throws RemoteException {
        if(turnPlayer == idPlayers.get(id) && turnPlayer.getTurn()) {
            currentClient = id;
            AbsCard cardAux = pushCard(absCard);
            return cardAux;
        } else {
            return absCard;
        }
    }

    //IServer
    @Override
    public ArrayList<Player> getPlayers() throws RemoteException {
        return players;
    }

    @Override
    public int addPlayer(String ip, String username, String password) throws RemoteException {
        Player player = new Player(username,ip);
        players.add(player);
        if (players.size() == 2){
            isReady = true;
        }
        ++playerID;
        playersID.put(player, playerID);
        idPlayers.put(playerID,player);
        turnPlayer = players.get(0);
        turnPlayer.setTurn(true);
        return playerID;
    }

    private boolean addToDraw(AbsCard card, int clientID) throws RemoteException{
        if(turnPlayer == idPlayers.get(clientID) &&  turnPlayer.getTurn() ) {
            idPlayers.get(clientID).addCard(card);
            turnPlayer.setPlayerDraw(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AbsCard> getDraw(int clientID) throws RemoteException {
        if(!idPlayers.containsKey(clientID)) {
            throw new RemoteException();
        }
        return idPlayers.get(clientID).getDeck();
    }

    @Override
    public AbsCard getTopCard() throws RemoteException {
        return topCard;
    }

    @Override
    public void setSelectedColor(int color) throws RemoteException {
        topCard = CardFactory.getColorCard(color);
    }

    @Override
    public void addObserver(Observer observer) throws RemoteException {
        observers.add(observer);
        clientObservers.put(observer.getID(), observer);
        notifyObservers();
    }

    @Override
    public void removeObserver(Observer observer) throws RemoteException{
        Player p = idPlayers.remove(observer.getID());
        playersID.remove(p);
        players.remove(p);
        observers.remove(observer);
        message += "***** "+ p.getUser() + " ha dejado la partida *****\n";
        notifyObservers();
    }

    @Override
    public void notifyObservers() throws RemoteException {
        for (Observer ob: observers) {
            ob.update();
        }
    }

    @Override
    public void notifyOb(int id) throws RemoteException {
        Observer o = clientObservers.get(id);
        o.chooseColor();
    }

    @Override
    public void notifyDraw(int n, int id) throws RemoteException {
        clientObservers.get(id).drawCards(n);
    }

    private void applyPower(AbsCard card) throws Exception {
        int turns = 2;
        if(Objects.equals(card.getClass(), Skip.class)){
        }else if(Objects.equals(card.getClass(), DrawTwo.class)) {
            notifyDraw(2,currentClient);
        }else if(Objects.equals(card.getClass(), DrawFour.class)){
            notifyDraw(4,currentClient);
        }else if(Objects.equals(card.getClass(), Reverse.class)) {
            turns = 1;
            Collections.reverse(players);
        }else{
            turns = 1;
        }
        nextTurn(turns);
    }

}
