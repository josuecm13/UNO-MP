package com.uno.cards;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class FactoryTest {

    @Test
    public void shouldCreateCard(){
            AbsCard card = CardFactory.getCard();
            Assert.assertNotNull(card);
    }

}
