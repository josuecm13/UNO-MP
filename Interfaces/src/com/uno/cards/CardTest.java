package com.uno.cards;

import com.uno.cards.special.DrawFour;
import com.uno.cards.special.Reverse;
import com.uno.cards.special.Skip;
import org.junit.Assert;
import org.junit.Test;

public class CardTest {

    @Test
    public void shouldBeSameName(){
        AbsCard card = new Skip();
        Assert.assertEquals(card.toString(), "Skip");
    }

    @Test
    public void shouldRecognizeClass(){
        AbsCard card = new DrawFour();
        Assert.assertEquals(card.getClass(), DrawFour.class);
    }

}
