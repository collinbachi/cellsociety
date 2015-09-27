# cellsociety
Load and run a variety of cellular automaton simulations.

Name: Collin Bachi, Jasper Hancock, Brenna Milligan

Date started: 9-10-15

Date finished: 9-27-15

Hours worked: 145

Resources used: Piazza, StackOverflow, oodesign.com

Main class file: Sets the scene and initializes the user interface

Data (non Java) files needed: All XML files for each simulation as well as the XML style sheets

How it works: The primary goal of this project is to simulate a variety of cellular automata within a user interface, based on the xml files that are selected as input. The simulation will be displayed on a grid, and the same grid should be capable of supporting any simulation that is fed into it via XML file. The primary design goals are having code that is flexible which is also done in a clean coding style. We hope to achieve this by having a hierarchy of simulations, which hopefully will make addition of new simulations simpler and keep our code regarding each simulation cleaner and more discrete. We are approximately following a Model, View, Controller pattern to keep our user interface and graphics separate from our game logic and resource files. The user should have the options to pause and increment the simulation, change the simulation speed and to switch simulations, and the simulation should be able to run indefinitely. This project needs to be open towards the types of simulations it can support with their different parameters, such as possible number of active number states or configuration of rules, and the visualization of different possible states must be clear to user during the simulation. This project needs to be closed in the way cells are updated, as all the cells in the grid need to be read and checked against the rules of the simulation in one pass and then updated in the next pass. 

Keys/Mouse input: Select the simulation from XML selector. Select a style sheet (optional) to change the style of the simulation. Click start to have it run indefinitely according to the slider bar speed or click increment to see one step at a time. Users may input their own parameter values on the right hand side of the interface and then click on change. Users may also generate a random distribution of cells. Users may click on each cell during the simulation or while the simulation is paused to change its state.

Known bugs: Infinite borders does not expand the scrolling screen although the scroll bar is appearing. The parameters inputted by the user are not checked for valid inputs (for example, some need to be < 1).

Extra features: Wrapping neighbors, different shapes, borders, new simulations, style sheets.

Impressions/Suggestions: There were so many additional instructions for the second week that we spent a lot of time implementing them instead of focusing on code design towards the very end. It was tough to balance time spent adding new features and time spent working on the code. However, this was a good project to introduce the idea of coding for extensibility.
