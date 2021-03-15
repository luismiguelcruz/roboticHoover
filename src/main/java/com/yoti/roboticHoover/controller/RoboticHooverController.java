package com.yoti.roboticHoover.controller;

import com.yoti.roboticHoover.model.Instructions;
import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.service.RoboticHooverService;
import com.yoti.roboticHoover.util.InstructionsWrapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoboticHooverController {
    @Autowired
    private RoboticHooverService roboticHooverService;

    @PostMapping("/hoover")
    public ResponseEntity<Object> performHooverAction(@RequestBody Instructions instructions) {
        final InstructionsWrapper instructionsWrapper = InstructionsWrapperUtils.buildInstrucctionsWrapper(instructions);

        if (instructionsWrapper.getBuildErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(roboticHooverService.moveRoboticHoover(instructionsWrapper));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(instructionsWrapper.getBuildErrors());
        }
    }
}
