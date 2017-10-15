package com.uno.players;

import java.io.Serializable;

/**
 * Created by ${gaboq} on 21/9/2017.
 */
public class Player implements Serializable {

    private String ip;
    private String user;
    int cards;
    private boolean turn;
    private boolean canDraw;

    public Player(String username, String ip){
        this.ip = ip;
        user = username;
        canDraw = true;
    }

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
