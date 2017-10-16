package com.uno.players;


public class Node{
    Player data;
    Node next;
    Node previous;

    public Node(Player data) {
        this.data = data;
        this.next = this.previous = null;
    }

    public Player getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrevious() {
        return previous;
    }
}
