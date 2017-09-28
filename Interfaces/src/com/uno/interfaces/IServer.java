package com.uno.interfaces;

import com.uno.cards.AbsCard;

import java.rmi.RemoteException;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public interface IServer extends ICard,IClient,Observable {

    String getPlayers() throws RemoteException;

    int addPlayer(String ip, String username, String password) throws RemoteException;

    AbsCard getTopCard() throws RemoteException;

    void setSelectedColor(int color) throws RemoteException;

}
