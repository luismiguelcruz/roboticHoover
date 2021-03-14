package com.yoti.roboticHoover.controller;

import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.RoboticHooverResponse;
import com.yoti.roboticHoover.service.RoboticHooverService;
import org.apache.commons.lang3.StringUtils;
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

    private InstructionsWrapper instructionWrapper;

    private String expectedMessage;

    private final String validRequest = "{\n" +
            "  \"roomSize\" : [5, 5],\n" +
            "  \"coords\" : [1, 2],\n" +
            "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
            "  \"instructions\" : \"NNESEESWNWW\"\n" +
            "}";

    private final String invalidParameterRequest = "{\n" +
            "  \"roomSize\" : [5],\n" +
            "  \"coords\" : [1, 2],\n" +
            "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
            "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
            "}";

    private final String missingParametherRequest = "{\n" +
            "  \"roomSize\" : [5, 5],\n" +
            "  \"patches\" : [[1, 0], [2, 2], [2, 3]],\n" +
            "  \"instructions\" : \"NNESEESWNWW\"\n" +
            "}";

    private String validResponse = "{\"coords\":[1,3],\"patches\":1}";

    @Before
    public void setup(){
    }

    @Test
    public void whenValidRequestIsSentThenReturnACorrectFinalPosition() throws Exception {

        doReturn(buildResponse(Arrays.asList(1, 3), 1)).when(roboticHooverService).moveRoboticHoover(any());

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hoover")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequest);

        final MvcResult result = mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(validResponse).isEqualTo(result.getResponse().getContentAsString());
    }

    @Test
    public void whenInvalidArgumentInRequestIsSentThenReturnAStringContainingErrorMessage() throws Exception{
        expectedMessage = "ERROR: Incorrect room size";

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hoover")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidParameterRequest);

        final MvcResult result = mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(expectedMessage);
    }

    @Test
    public void whenMissingParameterRequestIsSentThenReturnAStringContainingErrorMessage() throws Exception{
        expectedMessage = "ERROR: Incorrect initial coordinates";

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hoover")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(missingParametherRequest);

        final MvcResult result = mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).containsSequence(expectedMessage);
    }

    private RoboticHooverResponse buildResponse(final List<Integer> finalPosition, final int cleanedPatches) {
        return new RoboticHooverResponse(finalPosition, 1);
    }
}
