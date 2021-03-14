package com.yoti.roboticHoover.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Instructions {
    @NotNull
    private List<Integer> roomSize;

    @NotNull
    private List<Integer> coords;

    @NotNull
    private List<List<Integer>> patches;

    @NotNull
    private String instructions;

    public Instructions() {}

    public Instructions(List<Integer> roomSize, List<Integer> coords, List<List<Integer>> patches, String instructions) {
        this.roomSize = Objects.requireNonNull(roomSize);
        this.coords = Objects.requireNonNull(coords);
        this.patches = Objects.requireNonNull(patches);
        this.instructions = Objects.requireNonNull(instructions);
    }

    public List<Integer> getRoomSize() {
        return roomSize;
    }

    public List<Integer> getCoords() {
        return coords;
    }

    public List<List<Integer>> getPatches() {
        return patches;
    }

    public String getInstructions() {
        return instructions;
    }
}
