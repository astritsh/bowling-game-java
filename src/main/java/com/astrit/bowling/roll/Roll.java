package com.astrit.bowling.roll;

public class Roll {
    private static final int MIN_ROLL_VALUE = 0;
    private static final int MAX_ROLL_VALUE = 10;

    private final int points;

    public Roll(int points) {
        if (points < MIN_ROLL_VALUE || points > MAX_ROLL_VALUE) {
            throw new RuntimeException("Invalid roll points. Expected " + MIN_ROLL_VALUE + "-" + MAX_ROLL_VALUE);
        }
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public boolean isStrike() {
        return false;
    }

    public boolean isSpare() {
        return false;
    }

    public String getPrintValue() {
        return "" + points;
    }
}
