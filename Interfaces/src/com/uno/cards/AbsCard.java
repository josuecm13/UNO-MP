package com.uno.cards;

/*
 * Created by ${gaboq} on 26/9/2017.
 */

import java.io.Serializable;

public abstract class AbsCard implements Serializable{

    protected int number;

    protected int color;

    public void setNumber(int number) {
        this.number = number;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public boolean isSpecial() {
        return false;
    }

    public boolean isWild() {
        return false;
    }

}