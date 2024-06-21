package com.sushkomihail.unit;

public enum UnitTitle {
    UNINFECTED("Неболевши%s"),
    INFECTED("Больны%s"),
    RECOVERED("Выздоровевши%1$s/погибши%1$s");

    private final String title;

    public String getTitle(String ending) {
        return String.format(title, ending);
    }

    UnitTitle(String title) {
        this.title = title;
    }
}
