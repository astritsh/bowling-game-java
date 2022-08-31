package com.astrit.bowling.roll;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class SpareRollTest {

    @Test
    public void testObject() {
        SpareRoll spare = new SpareRoll(4);
        assertThat(spare.getPoints(), IsEqual.equalTo(4));
        assertThat(spare.isStrike(), IsEqual.equalTo(false));
        assertThat(spare.isSpare(), IsEqual.equalTo(true));
        assertThat(spare.getPrintValue(), IsEqual.equalTo("/"));
    }
}
