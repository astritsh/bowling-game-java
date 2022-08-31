package com.astrit.bowling.roll;

public class StrikeRoll extends Roll {

    public final static StrikeRoll INSTANCE = new StrikeRoll();

    private StrikeRoll() {
        super(10);
    }


    @Override
    public boolean isStrike() {
        return true;
    }

    @Override
    public String getPrintValue() {
        return "\tX";
    }
}
