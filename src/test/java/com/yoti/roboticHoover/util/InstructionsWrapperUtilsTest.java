package com.yoti.roboticHoover.util;

import com.google.common.collect.ImmutableList;
import com.yoti.roboticHoover.model.Instructions;
import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class InstructionsWrapperUtilsTest {
    @Before
    public void setUp() {

    }

    @Test
    public void testGenerateCoordinate() {
        final Position coordinate = InstructionsWrapperUtils.generateCoordinate(ImmutableList.of(1, 2));

        assertThat(coordinate.getX()).isEqualTo(1);
        assertThat(coordinate.getY()).isEqualTo(2);
    }

    @Test
    public void testGenerateWrongCoordinate() {
        final Position coordinate = InstructionsWrapperUtils.generateCoordinate(ImmutableList.of(1, 2, 3));

        assertThat(coordinate.getX()).isEqualTo(-1);
        assertThat(coordinate.getY()).isEqualTo(-1);
    }

    private Object[] validParametersToTest() {
        return new Object[] {
                new Object[] { new Instructions(
                        ImmutableList.of(5, 5),
                        ImmutableList.of(1, 2),
                        ImmutableList.of(ImmutableList.of(1, 0), ImmutableList.of(2, 2), ImmutableList.of(2, 3)),
                        "NNESEESWNWW") },
                new Object[] { new Instructions(
                        ImmutableList.of(5, 5),
                        ImmutableList.of(1, 2),
                        ImmutableList.of(),
                        "NNESEESWNWW") },
                new Object[] { new Instructions(
                        ImmutableList.of(5, 5),
                        ImmutableList.of(1, 2),
                        ImmutableList.of(ImmutableList.of(1, 0), ImmutableList.of(2, 2), ImmutableList.of(2, 3)),
                        "") }
        };
    }

    @Test
    @Parameters(method = "validParametersToTest")
    public void testBuildCorrectInstructionsWrapper(final Instructions instructions) {
        final InstructionsWrapper wrapper = InstructionsWrapperUtils.buildInstrucctionsWrapper(instructions);
        assertThat(wrapper.getBuildErrors()).hasSize(0);
    }


    private Object[] invalidParametersToTest() {
        return new Object[] {
                new Object[] { new Instructions(
                        ImmutableList.of(5),
                        ImmutableList.of(1, 2),
                        ImmutableList.of(ImmutableList.of(1, 0), ImmutableList.of(2, 2), ImmutableList.of(2, 3)),
                        "NNESEESWNWW"), "ERROR: Incorrect room size" },
                new Object[] { new Instructions(
                        ImmutableList.of(5, 5),
                        ImmutableList.of(1, 2, 3),
                        ImmutableList.of(ImmutableList.of(1, 0), ImmutableList.of(2, 2), ImmutableList.of(2, 3)),
                        "NNESEESWNWW"), "ERROR: Incorrect initial coordinates" },
                new Object[] { new Instructions(
                        ImmutableList.of(5, 5),
                        ImmutableList.of(1, 2),
                        ImmutableList.of(ImmutableList.of(1), ImmutableList.of(2, 2), ImmutableList.of(2, 3)),
                        "NNESEESWNWW"), "ERROR: Incorrect patch coordinates" },
                new Object[] { new Instructions(
                        ImmutableList.of(5, 5),
                        ImmutableList.of(1, 2),
                        ImmutableList.of(ImmutableList.of(1, 0), ImmutableList.of(2, 2), ImmutableList.of(2, 3)),
                        "Z"), "ERROR: Incorrect movement instruction \"Z\"" }
        };
    }

    @Test
    @Parameters(method = "invalidParametersToTest")
    public void testBuildIncorrectInstructionsWrapper(final Instructions instructions, final String expectedMessage) {
        final InstructionsWrapper wrapper = InstructionsWrapperUtils.buildInstrucctionsWrapper(instructions);

        assertThat(wrapper.getBuildErrors().size()).isGreaterThan(0);
        assertThat(wrapper.getBuildErrors().get(0)).isEqualTo(expectedMessage);
    }
}

