package com.yoti.roboticHoover.service;

import com.google.common.collect.ImmutableList;
import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;
import com.yoti.roboticHoover.model.RoboticHooverResponse;
import com.yoti.roboticHoover.util.Movement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoboticHooverServiceImpl implements RoboticHooverService {
    private InstructionsWrapper instructions;
    private Position currentPosition;
    private int cleanPatches;
    private List<Position> patches;

    @Override
    public RoboticHooverResponse moveRoboticHoover(final InstructionsWrapper instructions) {
        this.instructions = instructions;
        this.currentPosition = instructions.getInitialPosition();
        this.patches = new ArrayList<>();
        this.cleanPatches = 0;

        if (checkValidPosition(currentPosition)) {
            applyMovements();
        } else {
            currentPosition = new Position(-1, -1);
        }

        return new RoboticHooverResponse(ImmutableList.of(currentPosition.getX(), currentPosition.getY()), cleanPatches);
    }

    private void applyMovements() {
        instructions.getMovements().stream().forEach(x -> applySingleMovement(x));
    }

    // Applies a single movement to the hoover and set to the new position if the movement is allowed
    private void applySingleMovement(Movement movement) {
        final Position finalPosition = new Position(currentPosition.getX() + movement.getXMovement(),
                currentPosition.getY() + movement.getYMovement());

        // if the robot drive into a wall, the movement has no effect
        if (checkValidPosition(finalPosition)) {
            if (!patches.contains(finalPosition) && instructions.getPatches().contains(finalPosition)) {
                cleanPatches ++;
                patches.add(finalPosition);
            }

            currentPosition = finalPosition;
        }
    }

    // checks if the evaluated position is correct
    public boolean checkValidPosition(final Position position) {
        if (position.getX() < 1 || position.getX() > instructions.getMaxX() ||
                position.getY() < 1 || position.getY() > instructions.getMaxY()) {
            return false;
        }

        return true;
    }


    
    public Position getCurrentPosition() {
        return currentPosition;
    }

    public int getCleanedPatches() {
        return cleanPatches;
    }
}
