package com.yoti.roboticHoover.model;

import com.yoti.roboticHoover.util.Movement;

import java.util.ArrayList;
import java.util.List;

public class InstructionsWrapper {
    private Position roomSize;
    private Position initialPosition;
    private List<Position> patches;
    private List<Movement> movements;
    private List<String> buildErrors;

    public InstructionsWrapper(final Position roomSize, final Position initialPosition, final List<Position> patches,
                               final List<Movement> movements) {
        this.roomSize = roomSize;
        this.initialPosition = initialPosition;
        this.patches = patches;
        this.movements = movements;
        this.buildErrors = new ArrayList<>();
    }

    public InstructionsWrapper(final Position roomSize, final Position initialPosition, final List<Position> patches,
                               final List<Movement> movements, final List<String> buildErrors) {
        this.roomSize = roomSize;
        this.initialPosition = initialPosition;
        this.patches = patches;
        this.movements = movements;
        this.buildErrors = buildErrors;
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

    public List<String> getBuildErrors() {
        return buildErrors;
    }

    public int getMaxX() {
        return roomSize.getX();
    }

    public int getMaxY() {
        return roomSize.getY();
    }
}
