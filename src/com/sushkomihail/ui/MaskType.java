package com.sushkomihail.ui;

public enum MaskType {
    INT("^[0-9]+$"),
    FLOAT("^[0-9]+((\\.|\\,)[0-9]+)?$");

    private final String mask;

    public String getMask() {
        return mask;
    }

    MaskType(String mask) {
        this.mask = mask;
    }
}
