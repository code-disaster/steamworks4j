package com.codedisaster.steamworks;

import java.util.Arrays;

public enum SteamUniverse {
    Invalid(0),
    Public(1),
    Beta(2),
    Internal(3),
    Dev(4);

    private final int value;
    private static final SteamUniverse[] values = generateValues();

    private static final int maxValue() {
        SteamUniverse[] rawValues = values();
        int maxValue = rawValues[0].value;
        for (SteamUniverse au : rawValues) {
            if (maxValue < au.value) {
                maxValue = au.value;
            }
        }
        return maxValue;
    }

    private static final SteamUniverse[] generateValues() {
        SteamUniverse[] res = new SteamUniverse[maxValue() + 1];
        Arrays.fill(res, Invalid);

        for (SteamUniverse au : values()) {
            res[au.value] = au;
        }
        return res;
    }

    /**
     * get a SteamUniverse enum from value int
     *
     * @param value Input value int.
     * @return If the input value int have a matching enum, then return that enum. Otherwise return "Invalid"
     */
    static SteamUniverse byValue(int value) {
        if (value > 0 && value < values.length) {
            return values[value];
        }
        return Invalid;
    }

    SteamUniverse(int value) {
        this.value = value;
    }
}
