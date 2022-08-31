package com.astrit.bowling.roll;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class FailureRollTest {

    @Test
    public void testObject() {
        FailureRoll failure = FailureRoll.INSTANCE;
        assertThat(failure.getPoints(), IsEqual.equalTo(0));
        assertThat(failure.isStrike(), IsEqual.equalTo(false));
        assertThat(failure.isSpare(), IsEqual.equalTo(false));
        assertThat(failure.getPrintValue(), IsEqual.equalTo("F"));
    }
}
