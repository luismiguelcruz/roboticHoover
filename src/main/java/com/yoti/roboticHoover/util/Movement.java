package com.yoti.roboticHoover.util;

public enum Movement {
    NORTH("N", 0, 1),
    SOUTH("S", 0, -1),
    EAST("E", 1, 0),
    WEST("W", -1, 0);

    private String name;
    private int x;
    private int y;

    Movement(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    Movement(String name) {
        this.name = name;
        this.x = 0;
        this.y = 0;
    }

    public Movement getMovement() {
        return this;
    }

    public String getName() {
        return name;
    }

    public int getXMovement() {
        return x;
    }

    public int getYMovement() {
        return y;
    }
}