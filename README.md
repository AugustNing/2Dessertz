# voogasalad - 2 Dessertz

This project was the culminating project of Duke's Computer Science 308 class. Students work together for six weeks in a team of 10 to build a Game Engine that allows users to author games of a specific genre and play them in one application. Our team was created an engine for platform scroller games. We heavily utilized object-composition to keep a maximally flexible data structure for the core of the program. We utilized reflection heavily as well to dynamically build an authoring environment based on engine classes to maintain extensive flexibility.


### Team Members and Contributions 

Gouttham: Worked with Trishul, Martin and Yashas to create a game engine. Helped design the engine's code structure as well as implemented specific behaviors, and actions that GameElements can inherit.
Worked on coding the game-flow allowing user to travel from one gameLevel to another. 

Jeffrey: Worked with Calvin to make entire front end and back end of GamePlayer which manages selecting games, loading games, saving games, high scores, HUD,
changing key bindings dynamically, handling username, loading games from online.

Calvin: Worked with Jeffrey on the gameplayer. The gameplayer was used in order to initialize our engine and give the user some flexibility 
in terms of what games they want and how they wanted to interact with a game outside of the game itself such as keybindings and volume. Worked
the backend and frontend of this part, but mostly frontend as gameplayer was mainly a frontend thing. Also created two utilities for our group, 
one of which could not be reviewed by Facebook so we can't use it. Did like a couple things for the frontend for authoring. 

Yashas: Worked with Gouttham, Trishul, and Martin on the game engine component of the project. Helped design the inital structure for the Engine. Worked on implementing the display and audio aspects of the Engine. Also worked on impelemnting behaviors and game events related to audio playing, and level flow/transition. Also, worked with Gouttham to do a major refactoring of the Engine design to be more flexible for level flow and better understandable. Also, worked with Player team to integrate Engine display and Player's HUD and with Game Data team to integrate game data with Engine to be able to load and save games in Engine.

Martin: Worked on developing game engine. Developed collision logic, experimented with potential collision utility, created initial testing scenarios, integrated shape behaviors and hitboxes, worked on designing and writing code for game and element events, created old GameMetaData with Gouttham, made design decisions related to integrating game player with engine.

Trishul: Worked with Gouttham, Trishul, and Martin to build the GameEngine part of the project. We collaborated to come up with the original design for tbe composition style back end we have for the Engine. I built the GameElement class and many of the behaviors like Movable, Shootable, Movable Character, Time Routine, etc. I also worked with authoring to build the conversion tool from authoring environment objects into their engine equivalents. 

August: Worked with Edward to make game data to save and load various authoring and frontend elements to XML using XStream. Made various Authoring GUI frontend components such as the Event pop up window, Duplicate button, GameObject resizing, etc.

Summer: Maddie and I worked together on the authoring environment. We decided how the back-end should be set up, whether it should use the back-end engine or not, and how the GUIs should look. This would shape the user's game creating experience. In addition, I worked on integrating Groovy into the authoring environment and the engine.

Maddie: Worked with Summer to design the authoring backend; designed and implemented the initial authoring frontend before major refactorings midway through the project; made various authoring frontend components and popup windows

Edward: Works with August Ning to create the game data component of the project. Used XStream based serialization to store data files for game authoring and game engine analysis. Also worked extensively
on authoring component of project with August, Summer, and Maddie.



