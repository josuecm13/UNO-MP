package com.uno.interfaces;

import com.uno.cards.AbsCard;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Serializable {

    List<AbsCard> getDraw(int clientID) throws RemoteException, Exception;

    AbsCard getTopCard() throws  RemoteException;

    AbsCard generateCard(int userID) throws Exception;

    String getMessage() throws RemoteException;

    void writeMessage(String message) throws RemoteException;

    Boolean canDraw() throws RemoteException;

}
