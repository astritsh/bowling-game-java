package com.astrit.bowling;

import com.astrit.bowling.roll.Roll;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Player {
    private static final int MAX_FRAMES = 10;
    private final String name;
    private final List<Frame> frames;

    public String getName() {
        return name;
    }

    Player(String name) {
        this.name = name;
        frames = new ArrayList<>(MAX_FRAMES);
        Frame newFrame = new Frame(1);
        frames.add(newFrame);
    }

    void roll(String value) {
        if (isCompleted()) {
            throw new RuntimeException("Invalid Input - Player already completed all frames");
        }
        Frame frame = getLastFrame();
        frame.addRoll(value);
        setScoresAndBonuses();
        advanceFrame();
    }

    private void advanceFrame() {
        Frame currentFrame = getLastFrame();
        if (currentFrame.isCompleted() && frames.size() < MAX_FRAMES) {
            Frame newFrame = new Frame(currentFrame.getNumber() + 1);
            frames.add(newFrame);
        }
    }

    private void setScoresAndBonuses() {
        int points = 0;
        int currentRollIndex = 0;
        List<Roll> allRolls = frames.stream().flatMap(frame -> frame.getRolls().stream()).collect(Collectors.toList());
        Function<Integer, Integer> getRollPointsByIndex = (index) -> index < allRolls.size() ? allRolls.get(index).getPoints() : 0;
        for (Frame frame : frames) {
            int bonus = 0;
            points += frame.getSumOfRollPoints();
            if (frame.isStrike() && !frame.isLastFrame()) {
                bonus += getRollPointsByIndex.apply(currentRollIndex + 1) + getRollPointsByIndex.apply(currentRollIndex + 2);
            } else if (frame.isSpare() && !frame.isLastFrame()) {
                bonus += getRollPointsByIndex.apply(currentRollIndex + 2);
            }
            currentRollIndex += frame.getRolls().size();
            points += bonus;
            frame.setPoints(points);
            frame.setBonus(bonus);
        }
    }

    private boolean isCompleted() {
        return frames.size() == 10 && getLastFrame().isCompleted();
    }

    private Frame getLastFrame() {
        return frames.get(frames.size() - 1);
    }

    void printPinfalls() {
        System.out.print("Pinfalls\t");
        frames.forEach(Frame::printPinfalls);
    }

    void printScores() {
        System.out.print("Score\t\t");
        frames.forEach(Frame::printScore);
    }

    List<Frame> getFrames() {
        return frames;
    }
}
