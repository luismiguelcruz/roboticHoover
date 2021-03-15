
# Yoti test
+ [Overview](#overview)
+ [Statement](#Statement)
+ [Assumptions](#assumptions)
+ [Improvements](#improvements)
    
## Overview
Online java exercise for Yoti test. A service to move a robotic robot in a room.

## Statement
You will write a service that navigates a imaginary robotic hoover (much like a Roomba) through an equally imaginary room based on:

    room dimensions as X and Y coordinates, identifying the top right corner of the room rectangle. This room is divided up in a grid based on these dimensions; a room that has dimensions X: 5 and Y: 5 has 5 columns and 5 rows, so 25 possible hoover positions. The bottom left corner is the point of origin for our coordinate system, so as the room contains all coordinates its bottom left corner is defined by X: 0 and Y: 0.
    locations of patches of dirt, also defined by X and Y coordinates identifying the bottom left corner of those grid positions.
    an initial hoover position (X and Y coordinates like patches of dirt)
    driving instructions (as cardinal directions where e.g. N and E mean "go north" and "go east" respectively)

The room will be rectangular, has no obstacles (except the room walls), no doors and all locations in the room will be clean (hoovering has no effect) except for the locations of the patches of dirt presented in the program input.

Placing the hoover on a patch of dirt ("hoovering") removes the patch of dirt so that patch is then clean for the remainder of the program run. The hoover is always on - there is no need to enable it.

Driving into a wall has no effect (the robot skids in place).

## Assumptions
I have assumed the following assumptions for creating the code:

- In a grid of X columns and Y rows, I assume the first column/row starts in 1 instead of 0.
- In a grid of X columns and Y rows, I assume the the last X and Y coordinates as X and Y. Not X+1, Y+1.
- The controller is checking that the input instructions are correct all the time. It means:
  - Room size is formed by 2 parameters (X and Y).
  - Initial coordinate position is formed by 2 parameters (X and Y).
  - The patches could be empty. If it is not, all the coordinates should be composed by 2 parameters.
  - There are 4 possible movements N, S, E, W. Any other instruction, the program will show an error instead
   of ignoring it.
- I created an enum class that contains the allowed movements   
- About the movements in the grid, I got the next considerations:
  - N means Y+1
  - S means Y-1
  - E means X-1
  - W means X-1
- If the initial position of the hoover is an invalid one (for example the X axis is 5 and it's value is 6). 
I assume the server is returning a (-1, -1) value for this position instead of showing an error, in order to
make a treatment if it is needed later.
- If any movement on the hoover makes it to go out of the grid, the movement is not applied.
- To simplify, I assumed that InstructionsWrapper contains a list of errors that appeared during the
 Instructions parse.

## Improvements
- It could be interesting adding a properties file that contains the possible errors, and use it instead of the raw
 string values.
- Returning an error instead of (-1, -1) initial position when the initial coordinate has the correct size but it is
 out of the grid could be interesting, to keep the consistency of the errors.  
