package com.orange.plump.Solar.modules;

public class FpsBoostModule extends Module {

    private static String[] description = {"Boosts your fps"};

    public FpsBoostModule() {
        super("FpsBoost", description, "TOGGLED:TRUE");
    }

    public FpsBoostModule(int index) {
        super("FpsBoost " + index, description, "TOGGLED:TRUE");
    }


}
