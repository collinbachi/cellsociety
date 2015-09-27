# cellsociety
Load and run a variety of cellular automaton simulations.

Name: Collin Bachi, Jasper Hancock, Brenna Milligan

Date started: 9-10-15

Date finished: 9-27-15

Hours worked: 145

Team Roles: Collin - Front end, JavaFX, setting up grid. Jasper - Configuration, XML, creating style sheets and transferring data. Brenna - Back end, Java, creating simulations and simulation cells

Resources used: Piazza, StackOverflow, oodesign.com

Main class file: cellsociety_team09.Main.java

Data (non Java) files needed: All XML files for each simulation as well as the XML style sheets

How it works: Select the simulation from XML selector. Select a style sheet (optional) to change the style of the simulation. Click start to have it run indefinitely according to the slider bar speed or click increment to see one step at a time. Users may input their own parameter values on the right hand side of the interface and then click on change. Users may also generate a random distribution of cells. Users may click on each cell during the simulation or while the simulation is paused to change its state.

Known bugs: Infinite borders does not expand the scrolling screen although the scroll bar is appearing. The parameters inputted by the user are not checked for valid inputs (for example, some need to be < 1). Foraging Ants must be played at a low speed level, with the slider bar closer to 0. It takes a long time to run.

Extra features: Wrapping neighbors, different shapes, borders, new simulations, style sheets.

Impressions/Suggestions: There were so many additional instructions for the second week that we spent a lot of time implementing them instead of focusing on code design towards the very end. It was tough to balance time spent adding new features and time spent working on the code. However, this was a good project to introduce the idea of coding for extensibility.
