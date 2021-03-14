package com.yoti.roboticHoover.util;

import com.yoti.roboticHoover.model.Instructions;
import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yoti.roboticHoover.util.Movement.*;
import static com.yoti.roboticHoover.util.Movement.WEST;

public class InstructionsWrapperUtils {
    public static Position generateCoordinate(List<Integer> coordinates) {
        return new Position(coordinates.get(0), coordinates.get(1));
    }

    public static InstructionsWrapper buildInstrucctionsWrapper(final Instructions instructions) {
        final List<String> errors = new ArrayList();

        if (CollectionUtils.size(instructions.getRoomSize()) != 2) {
            errors.add("ERROR: Incorrect room size");
        }
        if (CollectionUtils.size(instructions.getCoords()) != 2) {
            errors.add("ERROR: Incorrect initial coordinates");
        }
        instructions.getPatches().stream().forEach(patch -> {
            if (CollectionUtils.size(patch) != 2) {
                errors.add("ERROR: Incorrect patch coordinates");
            }
        });
        if (!StringUtils.isBlank(instructions.getInstructions())) {
            final List<Movement> allowedMovements = Arrays.asList(Movement.values());
            Arrays.asList(instructions.getInstructions().split("")).stream()
                    .forEach(currentInstruction -> {
                        // Check if the movement is an allowed movement
                        final Optional<Movement> movementOpt = allowedMovements.stream()
                                .filter(allowedInstruction -> StringUtils.equals(allowedInstruction.getName(), currentInstruction))
                                .findAny();

                        if (!movementOpt.isPresent()) {
                            errors.add("ERROR: Incorrect movement instruction \""+currentInstruction+"\"");
                        }
                    });
        }


        if (!CollectionUtils.isEmpty(errors)) {
            return new InstructionsWrapper(new Position(0, 0), new Position(0, 0), Collections.emptyList(),
                    Collections.emptyList(), errors);
        }

        final Position roomSize = InstructionsWrapperUtils.generateCoordinate(instructions.getRoomSize());
        final Position initialPosition = InstructionsWrapperUtils.generateCoordinate(instructions.getCoords());
        final List<Position> patches = new ArrayList<>();
        instructions.getPatches().stream().forEach(patch -> patches.add(generateCoordinate(patch)));
        final List<Movement> movements = new ArrayList<>();
        Arrays.asList(instructions.getInstructions().split("")).stream()
                .forEach(currentInstruction -> {
                    Movement m = null;
                    switch(currentInstruction) {
                        case "N":
                            movements.add(NORTH);
                            break;
                        case "S":
                            movements.add(SOUTH);
                            break;
                        case "E":
                            movements.add(EAST);
                            break;
                        case "W":
                            movements.add(WEST);
                            break;
                    }
                });

        return new InstructionsWrapper(roomSize, initialPosition, patches, movements, errors);
    }
}
