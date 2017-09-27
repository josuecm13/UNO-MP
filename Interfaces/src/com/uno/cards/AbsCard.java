package com.uno.cards;

/*
 * Created by ${gaboq} on 26/9/2017.
 */

public abstract class AbsCard {

    protected int number;

    protected int color;

    protected String power;

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

    public String getPower() {
        return power;
    }

    public boolean isSpecial() {
        return false;
    }

    public boolean isWild() {
        return false;
    }

}