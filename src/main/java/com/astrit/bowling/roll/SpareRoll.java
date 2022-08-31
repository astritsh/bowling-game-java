package com.astrit.bowling.roll;

public class SpareRoll extends Roll {

    public SpareRoll(int points) {
        super(points);
    }

    @Override
    public boolean isSpare() {
        return true;
    }


    @Override
    public String getPrintValue() {
        return "/";
    }
}
