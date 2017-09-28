package com.uno.interfaces;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface Observer extends Serializable {
    void update() throws RemoteException;
    int getID() throws  RemoteException;
    void drawCards(int cant) throws  RemoteException;
    void chooseColor() throws RemoteException;
}
