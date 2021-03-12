package com.yoti.roboticHoover.service;

import com.yoti.roboticHoover.model.InstructionsWrapper;
import com.yoti.roboticHoover.model.Position;

public interface RoboticHooverService {

    Position moveRoboticHoover(final InstructionsWrapper instructions);
}
