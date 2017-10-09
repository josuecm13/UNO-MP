package com.uno.test;

import com.uno.players.CircularLinkedList;
import com.uno.players.Player;
import org.junit.Assert;
import org.junit.Test;


public class CircularLinkedListTest {

    @Test
    public void shouldCreateAPlayerAndAddedToAaList(){
        Player player = new Player("username","1.0.780.0.56");
        CircularLinkedList list = new CircularLinkedList();
        list.insert(player);
        Assert.assertEquals(player,list.remove(0));
    }

    @Test
    public void shouldFirstNodeEqualsLastNode(){
        Player player = new Player("username","1.0.780.0.56");
        CircularLinkedList list = new CircularLinkedList();
        list.insert(player);
        Assert.assertEquals(list.firstNode,list.lastNode);
    }

    @Test
    public void shouldMatchNextNode(){
        Player player = new Player("username","1.0.780.0.56");
        Player player1 = new Player("username1","1.0.780.0.56");
        Player player2 = new Player("username2","1.0.780.0.56");
        CircularLinkedList list = new CircularLinkedList();
        list.insert(player);
        list.insert(player1);
        list.insert(player2);
        list.remove(1);
        Assert.assertEquals(list.getFirstNode().getNext(), list.getLastNode());
    }

    @Test
    public void shouldMatchPrevNode(){
        Player player = new Player("username","1.0.780.0.56");
        Player player1 = new Player("username1","1.0.780.0.56");
        Player player2 = new Player("username2","1.0.780.0.56");
        CircularLinkedList list = new CircularLinkedList();
        list.insert(player);
        list.insert(player1);
        list.insert(player2);
        list.remove(1);
        Assert.assertEquals(list.getLastNode().getPrevious(), list.getFirstNode());
    }

    @Test
    public void shouldMathLength(){
        Player player = new Player("username","1.0.780.0.56");
        CircularLinkedList list = new CircularLinkedList();
        for (int i = 0; i < 70; i++) {
            list.insert(player);
        }
        Assert.assertEquals(list.getLength(),70);
    }



}
