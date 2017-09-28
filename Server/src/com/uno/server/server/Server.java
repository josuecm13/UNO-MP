package com.uno.server.server;

import com.uno.cards.AbsCard;
import com.uno.cards.CardFactory;
import com.uno.interfaces.IServer;
import com.uno.interfaces.Observer;
import com.uno.server.players.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements IServer, Serializable {

    private ArrayList<Player> players;
    private ArrayList<Observer> observers;
    private Map<Integer, ArrayList<AbsCard>> playerCards;
    private Map<Integer, Observer> clientObservers;
    private Map<Player, Integer> playersID;
    private AbsCard topCard;
    private static final long serialVersionUID = 1L;
    private int playerID = new Random().nextInt();
    private int currentClient;
    private Player turnPlayer;

    protected Server() throws RemoteException {
        players = new ArrayList<>();
        playerCards = new HashMap<>();
        observers = new ArrayList<>();
        playersID = new HashMap<>();
        clientObservers = new HashMap<>();
        topCard = getCard();
        //turnPlayer = players.get(0);
    }

    private void nextTurn(int turn) {
        int index = players.indexOf(turnPlayer);
        if(index + turn < players.size())
            turnPlayer = players.get(index + turn);
        else {
            index = index + turn % players.size();
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
        if(addToDraw(card,clientID))
            return card;
        return null;
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
            playerCards.remove(currentClient,card);
            card = null;
            endTurn();
            try {
                applypower(topCard.getPower());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    @Override
    public AbsCard pushHelper(AbsCard absCard, int id) throws RemoteException {
        if(turnPlayer.getTurn()) {
            currentClient = id;
            AbsCard cardAux = pushCard(absCard);
            notifyObservers();
            return cardAux;
        } else {
            return absCard;
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
        playersID.put(player, playerID);
        playerCards.put(playerID, new ArrayList<>());
        turnPlayer = players.get(0);
        turnPlayer.setTurn(true);
        return playerID;
    }

    private boolean addToDraw(AbsCard card, int clientID) throws RemoteException{
        if(turnPlayer.canDraw()) {
            if (!playerCards.containsKey(clientID)) {
                throw new RemoteException("No existe el usuario");
            }
            ArrayList<AbsCard> draw = playerCards.get(clientID);
            draw.add(card);
            playerCards.replace(clientID, draw);
            turnPlayer.setPlayerDraw(false);
            return true;
        } else {
            return false;
        }
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

    @Override
    public void setSelectedColor(int color) throws RemoteException {
        topCard = CardFactory.getColorCard(color);
    }

    @Override
    public void addObserver(Observer observer) throws RemoteException {
        observers.add(observer);
        clientObservers.put(observer.getID(), observer);
    }

    @Override
    public void removeObserver(Observer observer) throws RemoteException{
        observers.add(observer);
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
        clientObservers.get(id).chooseColor();
    }

    @Override
    public void notifyDraw(int n, int id) throws RemoteException {
        clientObservers.get(id).drawCards(n);
    }

    private void applypower(String power) throws Exception {
        int turns = 2;
        if(Objects.equals(power, "Skip")){
        }else if(Objects.equals(power, "DrawTwo")) {
            notifyDraw(2,currentClient);
        }else if(Objects.equals(power, "DrawFour")){
            notifyDraw(4,currentClient);
        }else if (Objects.equals(power, "Wild")) {
            notifyOb(currentClient);
        }else if(Objects.equals(power, "Reverse")) {
            turns = 1;
            Collections.reverse(players);
        }else{
            turns = 1;
        }
        nextTurn(turns);
    }

}
