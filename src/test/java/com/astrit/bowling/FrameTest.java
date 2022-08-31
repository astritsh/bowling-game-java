package com.astrit.bowling;

import com.astrit.bowling.roll.FailureRoll;
import com.astrit.bowling.roll.Roll;
import com.astrit.bowling.roll.StrikeRoll;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

public class FrameTest {

    private Frame frame;

    @BeforeEach
    public void setUp() {
        frame = new Frame(1);
    }

    @Test
    public void testGetSumOfRollPoints() {
        assertThat(frame.getSumOfRollPoints(), IsEqual.equalTo(0));
        frame.addRoll("3");
        assertThat(frame.getSumOfRollPoints(), IsEqual.equalTo(3));
        frame.addRoll("2");
        assertThat(frame.getSumOfRollPoints(), IsEqual.equalTo(5));
    }


    @Test
    public void testIsStrike() {
        assertFalse(frame.isStrike());
        frame.addRoll(StrikeRoll.INSTANCE.getPoints() + "");
        assertTrue(frame.isStrike());
    }

    @Test
    public void testIsSpare() {
        assertFalse(frame.isSpare());
        frame.addRoll("5");
        assertFalse(frame.isSpare());
        frame.addRoll("5");
        assertTrue(frame.isSpare());
    }

    @Test
    public void testAddRoll() {
        frame.addRoll(StrikeRoll.INSTANCE.getPoints() + "");

        //Confirm non-last frame can have only a single roll
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            frame.addRoll("3");
        });
        assertThat(exception.getMessage(), StringContains.containsString("Roll cannot be added. Frame already completed"));
    }

    @Test
    public void testValueToRollWithValidValues() {
        Roll roll = frame.valueToRoll("0");
        assertThat(roll.getPoints(), IsEqual.equalTo(0));
        assertThat(roll, instanceOf(Roll.class));

        roll = frame.valueToRoll("2");
        assertThat(roll.getPoints(), IsEqual.equalTo(2));
        assertThat(roll, instanceOf(Roll.class));

        roll = frame.valueToRoll("10");
        assertThat(roll.getPoints(), IsEqual.equalTo(10));
        assertThat(roll, IsEqual.equalTo(StrikeRoll.INSTANCE));

        roll = frame.valueToRoll("F");
        assertThat(roll.getPoints(), IsEqual.equalTo(0));
        assertThat(roll, IsEqual.equalTo(FailureRoll.INSTANCE));
    }

    @Test
    public void testValueToRollWithInValidValues() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            frame.valueToRoll("");
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll value. Expected 0-10 or F"));

        exception = assertThrows(RuntimeException.class, () -> {
            frame.valueToRoll(null);
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll value. Expected 0-10 or F"));


        exception = assertThrows(RuntimeException.class, () -> {
            frame.valueToRoll("-1");
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll value. Expected 0-10 or F. Received: -1"));


        exception = assertThrows(RuntimeException.class, () -> {
            frame.valueToRoll("aa");
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll value. Expected 0-10 or F. Received: aa"));

    }
}
