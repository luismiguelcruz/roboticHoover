package com.yoti.roboticHoover.service;

import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.RoboticHooverResponse;

public interface RoboticHooverService {

    RoboticHooverResponse moveRoboticHoover(final InstructionsWrapper instructions);
}
