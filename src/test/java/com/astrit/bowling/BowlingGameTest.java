package com.astrit.bowling;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class BowlingGameTest {
    private BowlingGame bowlingGame;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        bowlingGame = new BowlingGame();
    }

    @Test
    public void testAddRolls() {
        bowlingGame.addRoll(new InputRollValue("Astrit", "1"));
        bowlingGame.addRoll(new InputRollValue("Astrit", "2"));
        bowlingGame.addRoll(new InputRollValue("User", "2"));
        assertThat(bowlingGame.getPlayers().size(), IsEqual.equalTo(2));
        assertThat(bowlingGame.getPlayers().get(0).getFrames().size(), IsEqual.equalTo(2));
        assertThat(bowlingGame.getPlayers().get(1).getFrames().size(), IsEqual.equalTo(1));
    }


    @Test
    public void testPrintPrintResult() {
        bowlingGame.addRoll(new InputRollValue("Astrit", "1"));
        bowlingGame.printResult();
        assertThat(outputStreamCaptor.toString(), IsEqual.equalTo("Astrit\nFrame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\nPinfalls\t1\t\nScore\t\t1\t\t\n"));
    }
}
