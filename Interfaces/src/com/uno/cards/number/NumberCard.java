package com.uno.cards.number;


import com.uno.cards.AbsCard;

import java.rmi.Naming;

public class NumberCard extends AbsCard {

    public NumberCard() {
    }

    public NumberCard(int color) {
        this.number = -1;
        this.color = color;
    }

}
