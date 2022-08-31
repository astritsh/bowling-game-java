package com.astrit.bowling;

import com.astrit.bowling.roll.FailureRoll;
import com.astrit.bowling.roll.Roll;
import com.astrit.bowling.roll.SpareRoll;
import com.astrit.bowling.roll.StrikeRoll;

import java.util.ArrayList;
import java.util.List;

import static com.astrit.bowling.BowlingGame.MAX_FRAMES;

public class Frame {

    private static final int MAX_LAST_FRAME_ROWS = 3;
    private static final int MAX_FRAME_ROWS = 2;
    private static final int STRIKE_VALUE = 10;
    private static final int SPARE_VALUE = 10;

    private final int number;

    private int bonus;

    private int points;

    private final List<Roll> rolls;

    Frame(int number) {
        this.number = number;
        rolls = new ArrayList<>(2);
    }

    List<Roll> getRolls() {
        return rolls;
    }

    int getSumOfRollPoints() {
        return rolls.stream().mapToInt(Roll::getPoints).sum();
    }

    int getNumber() {
        return number;
    }

    public int getBonus() {
        return bonus;
    }

    boolean isStrike() {
        return rolls.stream().anyMatch(Roll::isStrike);
    }

    boolean isSpare() {
        return rolls.stream().anyMatch(Roll::isSpare);
    }

    void setBonus(int bonus) {
        this.bonus = bonus;
    }

    void setPoints(int points) {
        this.points = points;
    }

    void addRoll(String rollValue) {
        if (isCompleted()) {
            throw new RuntimeException("Roll cannot be added. Frame already completed");
        }
        Roll roll = valueToRoll(rollValue);
        rolls.add(roll);
    }

    boolean isCompleted() {
        if (isSpare() || isStrike()) {
            return lastFrameIsNotFilled();
        }
        return rolls.size() == MAX_FRAME_ROWS;
    }

    private boolean lastFrameIsNotFilled() {
        return !isLastFrame() || rolls.size() == MAX_LAST_FRAME_ROWS;
    }

    boolean isLastFrame() {
        return number == MAX_FRAMES;
    }


    public Roll valueToRoll(String value) {
        if (value == null || value.length() == 0) {
            throw new RuntimeException("Invalid roll value. Expected 0-10 or F");
        }
        if (value.equals("F")) {
            return FailureRoll.INSTANCE;
        }
        if (isNumeric(value)) {
            Integer intValue = Integer.parseInt(value);
            if (isStrike(intValue)) {
                return StrikeRoll.INSTANCE;
            }
            if (isSpare(intValue)) {
                return new SpareRoll(intValue);
            }
            return new Roll(intValue);
        }
        throw new RuntimeException("Invalid roll value. Expected 0-10 or F. Received: " + value);
    }

    private static boolean isStrike(int noOfPins) {
        return noOfPins == STRIKE_VALUE;
    }

    private boolean isSpare(int noOfPins) {
        return rolls.size() > 0 && rolls.get(rolls.size() - 1).getPoints() + noOfPins == SPARE_VALUE;
    }


    private static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    void printPinfalls() {
        for (Roll roll : rolls) {
            System.out.print(rolls.size() == MAX_LAST_FRAME_ROWS ? roll.getPrintValue().trim() : roll.getPrintValue());
            System.out.print("\t");
        }
    }

    void printScore() {
        if (rolls.size() > 0) {
            System.out.print(points + "\t\t");
        }
    }

    int getPoints() {
        return points;
    }
}
