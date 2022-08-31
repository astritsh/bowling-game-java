package com.astrit.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BowlingGame {

    static final int MAX_FRAMES = 10;
    private final ArrayList<Player> players = new ArrayList<>();

    void addRolls(RollProvider provider) {
        List<InputRollValue> rolls = provider.getRolls();
        rolls.forEach(this::addRoll);
    }

    void addRoll(InputRollValue roll) {
        Player player = getOrCreatePlayerByName(roll.getPlayerName());
        player.roll(roll.getRollValue());
    }

    private Player getOrCreatePlayerByName(String name) {
        Optional<Player> existingPlayer = players.stream().filter(player -> player.getName().equals(name)).findFirst();
        return existingPlayer.orElseGet(() -> {
            Player newPlayer = new Player(name);
            players.add(newPlayer);
            return newPlayer;
        });
    }

    ArrayList<Player> getPlayers() {
        return players;
    }

    void printResult() {
        for (Player player : players) {
            System.out.println(player.getName());
            System.out.print("Frame");
            for (int i = 1; i <= MAX_FRAMES; i++) {
                System.out.print("\t\t" + i);
            }
            System.out.println();
            player.printPinfalls();
            System.out.println();
            player.printScores();
            System.out.println();
        }
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Please provide a file");
        }
        BowlingGame game = new BowlingGame();
        String fileName = args[0];
        game.addRolls(new FileRollProvider(fileName));
        game.printResult();
    }
}
