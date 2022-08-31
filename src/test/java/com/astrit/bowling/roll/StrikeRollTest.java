package com.astrit.bowling.roll;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class StrikeRollTest {

    @Test
    public void testObject() {
        StrikeRoll strike = StrikeRoll.INSTANCE;
        assertThat(strike.getPoints(), IsEqual.equalTo(10));
        assertThat(strike.isStrike(), IsEqual.equalTo(true));
        assertThat(strike.isSpare(), IsEqual.equalTo(false));
        assertThat(strike.getPrintValue(), IsEqual.equalTo("\tX"));
    }
}
