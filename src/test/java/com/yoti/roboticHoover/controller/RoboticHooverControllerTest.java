package com.yoti.roboticHoover.controller;

import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;
import com.yoti.roboticHoover.model.RoboticHooverResponse;
import com.yoti.roboticHoover.service.RoboticHooverService;
import junitparams.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoboticHooverControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoboticHooverService roboticHooverService;

    private String expectedMessage;

    private String validResponse = "{\"coords\":[1,3],\"patches\":1}";

    @Before
    public void setup(){
    }

    @Test
    public void whenValidRequestIsSentThenReturnACorrectFinalPosition() throws Exception {
        final String validRequest = "{\n" +
                "  \"roomSize\" : [5, 5],\n" +
                "  \"coords\" : [1, 2],\n" +
                "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
                "  \"instructions\" : \"NNESEESWNWW\"\n" +
                "}";

        doReturn(buildResponse(Arrays.asList(1, 3), 1)).when(roboticHooverService).moveRoboticHoover(any());

        final MvcResult result = getMvcResult(validRequest, status().isOk());

        assertThat(result.getResponse().getContentAsString()).isEqualTo(validResponse);
    }

    @Test
    public void whenInvalidCoordinateSizeInRequestIsSentThenReturnAStringContainingErrorMessage() throws Exception{
        final String invalidRequest = "{\n" +
                "  \"roomSize\" : [5],\n" +
                "  \"coords\" : [1, 2],\n" +
                "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
                "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                "}";
        expectedMessage = "ERROR: Incorrect room size";

        final MvcResult result = getMvcResult(invalidRequest, status().isBadRequest());

        assertThat(result.getResponse().getContentAsString()).contains(expectedMessage);
    }

    @Test
    public void whenInvalidMovementInRequestIsSentThenReturnAStringContainingErrorMessage() throws Exception{
        final String invalidRequest = "{\n" +
                "  \"roomSize\" : [5, 5],\n" +
                "  \"coords\" : [1, 2],\n" +
                "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
                "  \"instructions\" : \"NZNESEESWNWW\"\n" +
                "}";
        expectedMessage = "ERROR: Incorrect movement instruction \\\"Z\\\"";

        final MvcResult result = getMvcResult(invalidRequest, status().isBadRequest());

        assertThat(result.getResponse().getContentAsString()).contains(expectedMessage);
    }

    @Test
    public void whenMissingParameterRequestIsSentThenReturnAStringContainingErrorMessage() throws Exception{
        final String missingParametherRequest = "{\n" +
                "  \"roomSize\" : [5, 5],\n" +
                "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
                "  \"instructions\" : \"NNESEESWNWW\"\n" +
                "}";
        expectedMessage = "ERROR: Incorrect initial coordinates";

        final MvcResult result = getMvcResult(missingParametherRequest, status().isBadRequest());

        assertThat(result.getResponse().getContentAsString()).containsSequence(expectedMessage);
    }

    private RoboticHooverResponse buildResponse(final List<Integer> finalPosition, final int cleanedPatches) {
        return new RoboticHooverResponse(finalPosition, 1);
    }

    @NotNull
    private MvcResult getMvcResult(String validRequest, ResultMatcher status) throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hoover")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequest);

        return mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status)
                .andReturn();
    }
}
