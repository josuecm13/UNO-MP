package com.uno.test;
import com.uno.players.Player;
import org.junit.Assert;
import org.junit.Test;
import sun.nio.cs.US_ASCII;


/**
 * Created by Admin on 9/10/2017.
 */
public class PlayerTest {

    @Test
    public void shouldMatchName(){
        String username = "genericUsername";
        Player player = new Player(username, "0");
        Assert.assertEquals(player.getUser(),username);
    }

    @Test
    public void shouldMatchIp(){
        String ip = "127.0.0.1";
        Player player = new Player("",ip);
        Assert.assertEquals(player.getIp(),ip);
    }

}
