package com.astrit.bowling.roll;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RollTest {

    @Test
    public void testObject() {
        Roll roll = new Roll(0);
        assertThat(roll.getPoints(), IsEqual.equalTo(0));
        assertThat(roll.isStrike(), IsEqual.equalTo(false));
        assertThat(roll.isSpare(), IsEqual.equalTo(false));
        assertThat(roll.getPrintValue(), IsEqual.equalTo("0"));

        roll = new Roll(1);
        assertThat(roll.getPoints(), IsEqual.equalTo(1));
        assertThat(roll.isStrike(), IsEqual.equalTo(false));
        assertThat(roll.isSpare(), IsEqual.equalTo(false));
        assertThat(roll.getPrintValue(), IsEqual.equalTo("1"));

        roll = new Roll(10);
        assertThat(roll.getPoints(), IsEqual.equalTo(10));
        assertThat(roll.isStrike(), IsEqual.equalTo(false));
        assertThat(roll.isSpare(), IsEqual.equalTo(false));
        assertThat(roll.getPrintValue(), IsEqual.equalTo("10"));
    }

    @Test
    public void createWithInvalidPoints() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Roll(-1);
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll points. Expected 0-10"));

        exception = assertThrows(RuntimeException.class, () -> {
            new Roll(11);
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll points. Expected 0-10"));

    }
}
