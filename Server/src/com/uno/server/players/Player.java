package com.uno.server.players;

import java.io.Serializable;

/**
 * Created by ${gaboq} on 21/9/2017.
 */
public class Player implements Serializable {

    String ip;
    String usuario;
    int cards;
    boolean turn;
    boolean canDraw;

    public Player(String username, String ip){
        this.ip = ip;
        usuario = username;
        canDraw = true;
    }

    public  String getPlayer(){
        return "Username: " + usuario + "\n" + "        Ip: " + ip;
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
