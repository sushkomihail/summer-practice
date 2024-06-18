package com.sushkomihail.ui;

public enum Mask {
    INT("^[0-9]+$"),
    FLOAT("^[0-9]+(\\.)?([0-9]+)?$");

    private final String mask;

    public String getMask() {
        return mask;
    }

    Mask(String mask) {
        this.mask = mask;
    }
}
