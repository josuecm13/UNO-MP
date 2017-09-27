package com.uno.server.players;

import java.io.Serializable;

/**
 * Created by ${gaboq} on 21/9/2017.
 */
public class Player implements Serializable {

    String ip;
    String usuario;
    int cards;

    public Player(String username, String ip){
        this.ip = ip;
        usuario = username;
    }

    public  String getPlayer(){
        return "Username: " + usuario + "\n" + "        Ip: " + ip;
    }

}
