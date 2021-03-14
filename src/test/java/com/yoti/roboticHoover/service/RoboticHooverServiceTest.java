package com.yoti.roboticHoover.service;

import com.google.common.collect.ImmutableList;
import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;
import com.yoti.roboticHoover.model.RoboticHooverResponse;
import com.yoti.roboticHoover.util.Movement;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static com.yoti.roboticHoover.util.Movement.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class RoboticHooverServiceTest {

    private RoboticHooverServiceImpl roboticHooverService;
    private RoboticHooverResponse response;

    @Before
    public void setUp() {
        roboticHooverService = new RoboticHooverServiceImpl();
    }

    @Test
    public void checkValidPositionReturnsCorrectPositionWhenItIsCorrect() {
        final InstructionsWrapper instructions = new InstructionsWrapper(new Position(3, 3), new Position(1, 1),
                Collections.emptyList(), Collections.emptyList());

        response = roboticHooverService.moveRoboticHoover(instructions);

        assertThat(response.getCoords().get(0) != -1 && response.getCoords().get(1) != -1);
    }

    private Object[] parametersToTestAdd() {
        return new Object[] {
                new Object[] { new Position(3, 3), new Position(-1, -1)},
                new Object[] { new Position(3, 3), new Position(-1, 1)},
                new Object[] { new Position(3, 3), new Position(1, -1)},
                new Object[] { new Position(3, 3), new Position(-1, 4)},
                new Object[] { new Position(3, 3), new Position(4, -1)},
                new Object[] { new Position(3, 3), new Position(1, 4)},
                new Object[] { new Position(3, 3), new Position(4, 1)},
        };
    }

    @Test
    @Parameters(method = "parametersToTestAdd")
    public void checkValidPositionReturnsInvalidPositionWhenItIsWrong(Position roomSize, Position initialPosition) {
        final InstructionsWrapper instructions = new InstructionsWrapper(roomSize, initialPosition,
                Collections.emptyList(), Collections.emptyList());

        response = roboticHooverService.moveRoboticHoover(instructions);

        assertThat(response.getCoords().get(0)).isEqualTo(-1);
        assertThat(response.getCoords().get(1)).isEqualTo(-1);
    }

    @Test
    public void checkValidMovementsReturnCorrectFinalPosition() {
        final List<Movement> movements = ImmutableList.of(NORTH, NORTH, EAST, SOUTH, EAST, EAST, SOUTH, WEST, NORTH, WEST, WEST);
        final InstructionsWrapper instructions = new InstructionsWrapper(new Position(5, 5), new Position(1, 2),
                Collections.emptyList(), movements);

        response = roboticHooverService.moveRoboticHoover(instructions);

        assertThat(response.getCoords().get(0)).isEqualTo(1);
        assertThat(response.getCoords().get(1)).isEqualTo(3);
    }

    @Test
    public void checkCorrectPatchesWithCorrectMovements() {
        final List<Movement> movements = ImmutableList.of(NORTH, NORTH, EAST, SOUTH, EAST, EAST, SOUTH, WEST, NORTH, WEST, WEST);
        final List<Position> patches = ImmutableList.of(
                new Position(1, 0),
                new Position(2, 2),
                new Position(2, 3)
        );

        final InstructionsWrapper instructions = new InstructionsWrapper(new Position(5, 5), new Position(1, 2),
                patches, movements);

        response = roboticHooverService.moveRoboticHoover(instructions);

        assertThat(response.getPatches()).isEqualTo(1);
    }

    /*@Test
    public void test() {
        final List<String> errors = new ArrayList();

        final String instructions = "NSEW";

        if (!StringUtils.isBlank(instructions)) {
            final List<Movement> allowedMovements = Arrays.asList(Movement.values());
            Arrays.asList(instructions.split("")).stream()
                    .forEach(currentInstruction -> {
                        // Check if the
                        final Optional<Movement> movementOpt = allowedMovements.stream()
                                .filter(allowedInstruction ->
                                    StringUtils.equals(allowedInstruction.getName(), currentInstruction))
                                .findAny();

                        if (!movementOpt.isPresent()) {
                            errors.add("ERROR: Incorrect movement instruction \""+currentInstruction+"\"");
                        }
                    });
        }

        final List<Movement> movements = new ArrayList<>();
        Arrays.asList(instructions.split("")).stream()
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

        assertThat(errors).hasSize(1);
    }*/
}
