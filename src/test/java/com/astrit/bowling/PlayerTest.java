package com.astrit.bowling;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {

    private Player player;
    private static final String NAME = "Astrit";

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        player = new Player(NAME);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void testGetName() {
        assertThat(player.getName(), IsEqual.equalTo(NAME));
    }

    @Test
    public void testPlayerHasInitialFrame() {
        assertThat(player.getFrames().size(), IsEqual.equalTo(1));
        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(0));
    }


    @Test
    public void testRollDefaultValues() {
        // First frame
        player.roll("5");
        player.roll("4");

        //Second frame
        player.roll("4");
        player.roll("3");

        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(9));
        assertThat(player.getFrames().get(1).getPoints(), IsEqual.equalTo(16));
        //Next frame is not played yet
        assertThat(player.getFrames().get(2).getPoints(), IsEqual.equalTo(0));
    }


    @Test
    public void testRollWithSpareValues() {
        // First frame
        player.roll("5");
        player.roll("5");

        assertThat(player.getFrames().get(0).isSpare(), IsEqual.equalTo(true));
        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(10));

        //Next frame is not played yet
        assertThat(player.getFrames().get(1).getPoints(), IsEqual.equalTo(0));
    }

    @Test
    public void testRollWithStrikeValue() {
        player.roll("10");

        player.roll("2");

        player.roll("3");

        player.roll("5");

        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(15));
        assertThat(player.getFrames().get(0).getBonus(), IsEqual.equalTo(5));

        assertThat(player.getFrames().get(1).getPoints(), IsEqual.equalTo(20));
        assertThat(player.getFrames().get(1).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(2).getPoints(), IsEqual.equalTo(25));
        assertThat(player.getFrames().get(2).getBonus(), IsEqual.equalTo(0));
    }

    @Test
    public void testRollWithAllStrikeValues() {
        for (int i = 0; i < 12; i++) {
            player.roll("10");
        }

        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(30));
        assertThat(player.getFrames().get(0).getBonus(), IsEqual.equalTo(20));

        assertThat(player.getFrames().get(1).getPoints(), IsEqual.equalTo(60));
        assertThat(player.getFrames().get(1).getBonus(), IsEqual.equalTo(20));

        assertThat(player.getFrames().get(8).getPoints(), IsEqual.equalTo(270));
        assertThat(player.getFrames().get(8).getBonus(), IsEqual.equalTo(20));

        assertThat(player.getFrames().get(9).getPoints(), IsEqual.equalTo(300));
        assertThat(player.getFrames().get(9).getBonus(), IsEqual.equalTo(0));
    }

    @Test
    public void testRollWithAllStrikeValuesExceedingMaxRolls() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i < 13; i++) {
                player.roll("10");
            }
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid Input - Player already completed all frames"));
    }

    @Test
    public void testRollWithAllFailureValues() {
        for (int i = 0; i < 20; i++) {
            player.roll("F");
        }
        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(0).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(1).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(1).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(8).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(8).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(9).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(9).getBonus(), IsEqual.equalTo(0));


        // try another roll after game is competed
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            player.roll("F");
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid Input - Player already completed all frames"));
    }


    @Test
    public void testRollWithAllZeroValues() {
        for (int i = 0; i < 20; i++) {
            player.roll("0");
        }

        assertThat(player.getFrames().get(0).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(0).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(1).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(1).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(8).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(8).getBonus(), IsEqual.equalTo(0));

        assertThat(player.getFrames().get(9).getPoints(), IsEqual.equalTo(0));
        assertThat(player.getFrames().get(9).getBonus(), IsEqual.equalTo(0));


        // try another roll after game is competed
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            player.roll("0");
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid Input - Player already completed all frames"));
    }

    @Test
    public void testPrintPinfalls() {
        player.roll("2");
        player.roll("3");
        player.printPinfalls();
        assertThat(outputStreamCaptor.toString(), IsEqual.equalTo("Pinfalls\t2\t3\t"));
    }

    @Test
    public void testPrintPinfallsStrike() {
        player.roll("10");
        player.printPinfalls();
        assertThat(outputStreamCaptor.toString(), IsEqual.equalTo("Pinfalls\t\tX\t"));
    }

    @Test
    public void testPrintScores() {
        player.roll("2");
        player.roll("3");
        player.printScores();
        assertThat(outputStreamCaptor.toString(), IsEqual.equalTo("Score\t\t5\t\t"));
    }

    @Test
    public void testPrintStrikeScores() {
        player.roll("10");
        player.printScores();
        assertThat(outputStreamCaptor.toString(), IsEqual.equalTo("Score\t\t10\t\t"));
    }
}
