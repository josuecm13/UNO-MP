package com.uno.test;

import com.uno.client.Controller;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * Created by Admin on 9/10/2017.
 */
public class ControllerTest {


    @Test
    public void shouldGetInstance() throws Exception {
        Controller c = Controller.getInstance();
        Assert.assertEquals(c,Controller.getInstance());
    }

    @Test
    public void shouldConnectToServer() throws RemoteException{
        try {
            Assert.assertNotNull(Controller.getInstance());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetCard() throws Exception {
        Controller c = Controller.getInstance();
        Assert.assertNotNull(c.getCard());
    }

}
