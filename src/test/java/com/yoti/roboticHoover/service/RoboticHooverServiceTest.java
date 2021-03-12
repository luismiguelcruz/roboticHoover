package com.yoti.roboticHoover.service;

import com.google.common.collect.ImmutableList;
import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;
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
    private Position finalPosition;

    @Before
    public void setUp() {
        roboticHooverService = new RoboticHooverServiceImpl();
    }

    @Test
    public void checkValidPositionReturnsCorrectPositionWhenItIsCorrect() {
        final InstructionsWrapper instructions = new InstructionsWrapper(new Position(3, 3), new Position(1, 1),
                Collections.emptyList(), Collections.emptyList());

        roboticHooverService.moveRoboticHoover(instructions);

        finalPosition = roboticHooverService.getCurrentPosition();

        assertThat(finalPosition.getX() != -1 && finalPosition.getY() != -1);
    }

    private Object[] parametersToTestAdd() {
        return new Object[] {
                //new Object[] { new Position(3, 3), new Position(-1, -1)},
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

        roboticHooverService.moveRoboticHoover(instructions);

        finalPosition = roboticHooverService.getCurrentPosition();

        assertThat(finalPosition.getX()).isEqualTo(-1);
        assertThat(finalPosition.getY()).isEqualTo(-1);
    }

    @Test
    public void checkValidMovementsReturnCorrectFinalPosition() {
        final List<Movement> movements = ImmutableList.of(NORTH, NORTH, EAST, SOUTH, EAST, EAST, SOUTH, WEST, NORTH, WEST, WEST);
        final InstructionsWrapper instructions = new InstructionsWrapper(new Position(4, 4), new Position(0, 1),
                Collections.emptyList(), movements);

        roboticHooverService.moveRoboticHoover(instructions);

        finalPosition = roboticHooverService.getCurrentPosition();

        assertThat(finalPosition.getX()).isEqualTo(0);
        assertThat(finalPosition.getY()).isEqualTo(2);
    }

    @Test
    public void checkCorrectPatchesWithCorrectMovements() {
        final List<Movement> movements = ImmutableList.of(NORTH, NORTH, EAST, SOUTH, EAST, EAST, SOUTH, WEST, NORTH, WEST, WEST);
        final List<Position> patches = ImmutableList.of(
                new Position(0, -1),
                new Position(1, 1),
                new Position(1, 2)
        );

        final InstructionsWrapper instructions = new InstructionsWrapper(new Position(4, 4), new Position(0, 1),
                patches, movements);

        roboticHooverService.moveRoboticHoover(instructions);

        assertThat(roboticHooverService.getCleanedPatches()).isEqualTo(1);
    }
}
