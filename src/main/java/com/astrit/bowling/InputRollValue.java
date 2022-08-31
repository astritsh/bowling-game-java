package com.astrit.bowling;

public class InputRollValue {
    private final String playerName;
    private final String rollValue;

    public InputRollValue(String name, String rollValue) {
        this.playerName = name;
        this.rollValue = rollValue;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRollValue() {
        return rollValue;
    }
}
