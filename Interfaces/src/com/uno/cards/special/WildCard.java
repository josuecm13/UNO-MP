package com.uno.cards.special;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class WildCard extends SpecialCard{

    @Override
    public void setColor(int color) {}

    public WildCard(){
        number = 13;
        color = 4;
    }

    @Override
    public boolean isWild() {
        return true;
    }
}
