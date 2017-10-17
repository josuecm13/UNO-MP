package com.uno.players;

import com.uno.cards.AbsCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${gaboq} on 21/9/2017.
 */
public class Player implements Serializable {

    private String ip;
    private String user;
    private List<AbsCard> deck;
    private boolean turn;
    private boolean canDraw;

    public Player(String username, String ip){
        this.ip = ip;
        user = username;
        canDraw = true;
        deck = new ArrayList<>();
    }

    public void addCard(AbsCard card) { deck.add(card);}

    public void removeCard(AbsCard card) { deck.remove(card); }

    public List<AbsCard> getDeck() { return deck; }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public  String getPlayer(){
        return "Username: " + user + "\n" + "        Ip: " + ip;
    }

    public boolean getTurn() {
        return turn;
    }

    public boolean canDraw() {
        return canDraw;
    }

    public void setPlayerDraw(boolean bool) {
        canDraw = bool;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
