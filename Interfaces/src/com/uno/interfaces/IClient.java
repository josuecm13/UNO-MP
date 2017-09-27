package com.uno.interfaces;

import com.uno.cards.AbsCard;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface IClient extends Serializable {

    String getDraw(int clientID) throws RemoteException, Exception;

    AbsCard getTopCard() throws  RemoteException;

    AbsCard generateCard(int userID) throws Exception;

}
