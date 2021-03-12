package com.yoti.roboticHoover.model;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Position)) {
            return false;
        }

        final Position position = (Position) o;
        return this.getX() == position.getX() && Objects.equals(this.getY(), position.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
