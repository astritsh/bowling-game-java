package com.astrit.bowling;


import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BowlingGameFileTest {
    private BowlingGame game;

    @BeforeEach
    public void setUp() {
        game = new BowlingGame();
    }


    @Test
    public void testFilePerfect() {
        final String fileName = "src/test/resources/positive/perfect.txt";
        game.addRolls(new FileRollProvider(fileName));
        assertEquals(game.getPlayers().size(), 1);
        Player player = game.getPlayers().get(0);
        assertEquals("Carl", player.getName());
        assertEquals(10, player.getFrames().size());
        assertEquals(30, player.getFrames().get(0).getPoints());
        assertEquals(60, player.getFrames().get(1).getPoints());
        assertEquals(300, player.getFrames().get(9).getPoints());
    }

    @Test
    public void testFileSores() {
        final String fileName = "src/test/resources/positive/scores.txt";
        game.addRolls(new FileRollProvider(fileName));
        assertEquals(game.getPlayers().size(), 2);
        Player player1 = game.getPlayers().get(0);
        assertEquals("Jeff", player1.getName());
        assertEquals(10, player1.getFrames().size());
        assertEquals(20, player1.getFrames().get(0).getPoints());
        assertEquals(39, player1.getFrames().get(1).getPoints());
        assertEquals(167, player1.getFrames().get(9).getPoints());
        Player player2 = game.getPlayers().get(1);
        assertEquals("John", player2.getName());
        assertEquals(10, player2.getFrames().size());
        assertEquals(16, player2.getFrames().get(0).getPoints());
        assertEquals(25, player2.getFrames().get(1).getPoints());
        assertEquals(151, player2.getFrames().get(9).getPoints());
    }


    @Test
    public void testFileEmpty() {
        final String fileName = "src/test/resources/negative/empty.txt";
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            game.addRolls(new FileRollProvider(fileName));
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid File. No rolls found."));
    }


    @Test
    public void testFileExtraScore() {
        final String fileName = "src/test/resources/negative/extra-score.txt";
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            game.addRolls(new FileRollProvider(fileName));
        });
        assertThat(exception.getMessage(), IsEqual.equalTo("Invalid Input - Player already completed all frames"));
    }


    @Test
    public void testFileFreeText() {
        final String fileName = "src/test/resources/negative/free-text.txt";
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            game.addRolls(new FileRollProvider(fileName));
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid input: Lorem ipsum dolor sit amet,"));
    }

    @Test
    public void testFileInvalidScore() {
        final String fileName = "src/test/resources/negative/invalid-score.txt";
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            game.addRolls(new FileRollProvider(fileName));
        });
        assertThat(exception.getMessage(), StringContains.containsString("Invalid roll value. Expected 0-10 or F. Received: lorem"));
    }

}
