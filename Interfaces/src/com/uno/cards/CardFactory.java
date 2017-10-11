package com.uno.cards;


import com.uno.cards.number.NumberCard;
import com.uno.cards.special.SpecialCard;

import java.io.Serializable;

/**
 * Created by ${gaboq} on 21/9/2017.
 */


public class CardFactory implements Serializable{

    public static int generateRandom(int n){
        return (int) (Math.random() * (n+1));
    }

    private static void setCardProperties(AbsCard card){
        card.setColor(generateRandom(3));
        if(!card.isSpecial()) {
            card.setNumber(generateRandom(9));
        }
    }

    public static AbsCard getCard(){
        int rand = generateRandom(100);
        AbsCard card;
        if(rand <= 68){
            card = new NumberCard();
        } else {
            card = new SpecialCard().getSpecial();
        }
        setCardProperties(card);
        return card;
    }

    public static AbsCard getColorCard(int color) {
        return new NumberCard(color);
    }

}
