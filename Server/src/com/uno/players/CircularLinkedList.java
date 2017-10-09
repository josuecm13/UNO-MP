package com.uno.players;

/**
 * Created by Admin on 8/10/2017.
 */
public class CircularLinkedList {

    public Node firstNode;
    public Node lastNode;
    public int length;

    public CircularLinkedList(){
        firstNode = lastNode = null;
        length = 0;
    }

    public void insert(Player player){
        if(firstNode == null){
            firstNode = lastNode = new Node(player);
            lastNode.next = lastNode.previous = firstNode;
        }else{
            Node newNode = new Node(player);
            lastNode.next =newNode;
            newNode.previous = lastNode;
            lastNode = lastNode.next;
            lastNode.next = firstNode;
        }
        length++;
    }

    public Player remove(int index){
        if(!(index > length || firstNode == null)){
            Node tmp = firstNode;
            int i;
            for(i = 0; i < index && tmp != null; i++ ){
                tmp= tmp.next;
            }if(i == index){
                tmp.previous.next = tmp.next;
                tmp.next.previous = tmp.previous;
                tmp.previous = tmp.next = null;
                return tmp.data;
            }
        }
        return null;
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public Node getLastNode() {
        return lastNode;
    }

    public int getLength() {
        return length;
    }
}
