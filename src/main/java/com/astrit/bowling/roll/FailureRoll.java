package com.astrit.bowling.roll;

public class FailureRoll extends Roll {

    public final static FailureRoll INSTANCE = new FailureRoll();

    private FailureRoll() {
        super(0);
    }

    @Override
    public String getPrintValue() {
        return "F";
    }
}
