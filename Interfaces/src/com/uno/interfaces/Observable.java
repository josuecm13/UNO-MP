package com.uno.interfaces;

import com.uno.cards.AbsCard;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface Observable extends Serializable{
    void addObserver(Observer o) throws RemoteException;
    void removeObserver(Observer o) throws RemoteException;
    void notifyObservers() throws RemoteException;
    void notifyOb(int id) throws RemoteException;
    void notifyDraw(int n, int id) throws RemoteException;
}
