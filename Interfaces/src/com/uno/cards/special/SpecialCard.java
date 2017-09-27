package com.uno.cards.special;


import com.uno.cards.AbsCard;
import com.uno.cards.CardFactory;

public class SpecialCard extends AbsCard {

    @Override
    public boolean isSpecial(){
        return true;
    }

    public AbsCard getSpecial() {
        int rand = CardFactory.generateRandom(100);
        AbsCard card;
        if(rand <= 25){
            card = new Skip();
        } else if(rand <= 50) {
            card = new Reverse();
        } else if(rand <= 75) {
            card = new DrawTwo();
        } else {
            WildCard wild;
            if (rand <= 88) {
                wild = new Wild();
                card = wild;
            } else {
                wild = new DrawFour();
                card = wild;
            }
        }
        return card;
    }

}
