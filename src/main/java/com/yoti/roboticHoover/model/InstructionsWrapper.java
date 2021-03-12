package com.yoti.roboticHoover.model;

import com.yoti.roboticHoover.util.Movement;

import java.util.List;

public class InstructionsWrapper {
    private Position roomSize;

    private Position initialPosition;

    private List<Position> patches;

    private List<Movement> movements;

    public InstructionsWrapper(Position roomSize, Position initialPosition, List<Position> patches, List<Movement> movements) {
        this.roomSize = roomSize;
        this.initialPosition = initialPosition;
        this.patches = patches;
        this.movements = movements;
    }

    public Position getRoomSize() {
        return roomSize;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public List<Position> getPatches() {
        return patches;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public int getMaxX() {
        return roomSize.getX();
    }

    public int getMaxY() {
        return roomSize.getY();
    }
}
