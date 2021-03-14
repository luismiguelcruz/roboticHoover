package com.yoti.roboticHoover.model;

import java.util.List;

public class RoboticHooverResponse {
    private List<Integer> coords;
    private int patches;

    public RoboticHooverResponse(final List<Integer> coords, final int patches) {
        this.coords = coords;
        this.patches = patches;
    }

    public RoboticHooverResponse() {
    }

    public List<Integer> getCoords() {
        return coords;
    }

    public void setCoords(List<Integer> coords) {
        this.coords = coords;
    }

    public int getPatches() {
        return patches;
    }

    public void setPatches(int patches) {
        this.patches = patches;
    }
}
