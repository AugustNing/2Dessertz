## [VOOGASalad](https://www2.cs.duke.edu/courses/compsci308/spring18/assign/04_voogasalad/index.php) : Design Plan


**Introduction**  

The goal of this project is to create a flexible, object-oriented, and well designed game authoring and game playing environment for scrolling platformer games. The design goals of this project is to create a way for people to easily create their own scrolling platform game, and also give them a way to play their game. We want to create this project by splitting it into specific, modularized packages with clear internal and external API that can create a variety of games. For scrolling platfomers, we have to considering various game elements (playable characters, enemies, environment blocks), their interactions, and engines for running the guidelines of the game.
    
**Overview** 

We split the project up in 5 parts: Game Authoring, Game Engine, Game Player, Game Data, and Display. They were split to have clear, encapsulated, and concise API/methods, and we can use them together to achieve our goal of making a game authoring and playing environment. The five work together, such that game authoring creates games, game data reads in information from game authoring, and sends it to the game engine, where the game is run along with the game player.

* Authoring
    The Authoring deals with creating custom games and levels. This includes creating objects that can be placed in the game and determining what actions lead to which events. The authoring environment will not have implementation for any of the game elements or the properties of these elements, but will store them as strings. When the files are loaded into the program to be played, the game engine and player will have the needed implementations. The only part of the project that the authoring environment is directly dependent on is the game data. It will be writing files out to game data as well as reading them back in. 
    The authoring environment will contain classes such as GameObject, Property, GameScene, and ObjectMaker. These classes are essentially Java class representations of the data that represents a game within the XML documents.
    
* Engine
    The Engine is the framework to run any game created in the Authoring environment. At a high level, the engine encompasses the game logic (game elements and behaviors of game elements), game loop (game flow control and handling input and events in the game), and rendering/displaying the game. When a game is run, the game engine uses the resource files of the specific game created in the authoring environment and instantiates game element object. The game loop runs to step the game and handle events. The game will be rendered and displayed onto a JavaFX object that will be returned to the Player to be displayed.

* Player
    The Player essentially acts as a manager for playing games. It is a framework for managing all games in general and is unaffeced by specific game implementations. Regarding features, it allows the user to select which game to play, restart games, change games, load games, save games, all without exiting the window and restarting the application.
    
    The Player communicates with the Engine and Display. 
    
    The External API of the Player will have two main methods. InitalizePlayer will be called by the Display when the user decides to start playing a game. UpdateHUD will be called by the Game Engine, passing game information to be displayed on the Player.
    
    The Player will call the Engine's external API in order to getState info for saving a game, and InitialiseGame in order to create a new game (or load an old game state).
    
* Game Data

    The Game Data is responsible for maintaining game data information which can be accessed by both the Game Authoring Environment and Game Engine. The Game Authoring Engine should be able to create a new game and store it within the Game Data Module in the form of an XML file and accompanying images. The Game Engine should be able to access this game and play it, and it should also be able to save a game state. 

* Display
    The Display can be split up between game authoring and game player/engine. For game player/engine, the display will show the playing of the game, and also any relevant/required user interactions, such as buttons and key presses. The display will communicate event handlers to the backend, and backend will send updates to frontend, similar to a MVC design pattern. Keeping the display and the player/engine seperate keeps frontend and backend seperate, and keeps the modules encapsulated.
    The display for the game authoring environment will resemble the game authoring environment in the Unity game engine. This display will only interact with the rest of the game authoring environment and will not have dependencies with the other part of the project. The only other part of the project it interacts with is the game data.
    
**User Interface** 
    
    
The general user interface will start with a splash screen, where a user can select between playing the game (to game player) or edit a game (to game authoring). Upon pressing the play game selection, the user will have the option to select a game data file that will be read in and then played by the game engine. If the authoring environment option is selected, then there will be an option to author an existing game or start from scratch. 
    
For game playing, it will consist of the game player and game engine. The user can play their selected game here, and do related operations such as restart, quit, share, etc. These aspects that are separated from different games are part of the game player. The game player will look like a border around the "game engine display" with various options for restarting, choosing new game, etc. 
    
The Game engine controls the user interface once the user chooses a game. The engine will create a splash screen that allows the user to choose the game specific level that they would like to play. This will allow the users to choose levels creatively (ex: Having the main character walk through portals, one for each level). After the user chooses a level, the user can interact with the main character using keys and mouse input event handlers These game interactions such as player/enemy movement, environment build, HUD, is also housed in this code as part of the game engine. The user will be able to use these key input and mouse handlers to travel from one level to the next when the main character touches objects within the screen that represent transitional elements from one level to the next. 

One problem we have to handle is when a user tried to play or edit a game when they do not specify a file. We can do prevent this by catching null input from the uploaded file, and throw an alert box to prevent the program from breaking. This is can also be done if a game failed to load, such as if an improper file is uploaded. 

If the user selects the option to create or edit a game from the splash screen, she will be taken to the game authoring environment. The user can choose to create a game from scratch, or upload the data files for an existing game and continue to edit that game. The game authoring user interface will resemble the user interface of the [Unity game engine](unityScreenshot.png), in which the user is able to design their own game. We will obviously not be able to implement all of the Unity functions visible here, but the aspects we will emulate are the list of GameObjects (seen on the left in the picture), the visual game display (seen in the center), GameOhe list of properties of the currently selected object (seen on the right), and a file explorer to see all of the available resources of the game (seen on the bottom).

The visual game display will be a screen in the middle that shows the game, where the user can drag and place GameObjects that he or she has created. 

There will be two lists of GameObjects that the user can see: one list of GameObjects that have been placed in the game, and another list of "template" GameObjects that the user has created (similar to prefabs in Unity). If the user clicks on a GameObject, he or she will be able to edit the properties for that GameObject. The user will also be able to specify aspects such as what happens when the right arrow is pressed.

The file explorer will show the user the available resources for this game, such as images or sounds, that they can attach to GameObjects.

When the user is done editing the game, he or she can press the "save" button, which will write out all aspects of the game into an XML document that can be read in by the authoring environment and edited again, or that can be read by the game engine and played in the playing environment. 

This environment will simplify creating a game for the user by easily allowing the user to create GameObjects and add and edit the properties of those GameObjects. The current plan is to allow the user to accomplish these tasks both by clicking the appropriate buttons and by dragging and dropping elements.

Erroneous situations that will be reported to the user include any data that has been left blank or inputting data of the wrong type to an object property.

**Design Details**

Game Engine:
* The core of the game engine will be a system that controls interactions between game elements and holds the state of a game in its own object. Our design allows any game component to be implemented as a collection of properties and behaviors that outline the individual element's interactions with other objects. The engine itself will process our own custom-defined events that are passed to it and tell each object to act accordingly. The general outline of the game is as follows
    1. The game engine core will receive a "time-step" event from whatever is controlling the core.
    2. The core will alert each element to react to this "time-step" event. As an example, objects that are currently in motion will move their positions according to their velocity when they receive a time-step event. Objects that are stationary will not have a noticeable reaction to the "time-step" event
    3. The core will check the positions of every node and note any collisions that occur. For each collision that occurs, the core will alert the game elements involved in the collision to react according to how the game element is defined. The elements that collide will know about each other.
    4. The elements will change their properties according to the time-step/collision/key-press events and any other event type that we think of later. 
    5. The game displayer will view the Model object and display it accordingly to the user.
    6. The user will provide input to the game engine and this cycle will continue.

* If the game engine is told to pause at any point, all that is required is for whatever feeds events to the core to stop feeding any events. The model will be frozen and therefore, so will the display.

* The game engine is also responsible for controlling the game flow. To simplify needing a second game flow managing structure, we will implement the game flow as a level. In our game, we will need elements that signify the end of the game and can transfer to other levels. We will implement a collection of these to be our level chooser in our game flow. The game flow control will be implemented as a mini-game in and of itself.


Game Player:
* The game player is necessary for various games to be played within our program. Whenever the GUI (display) indicates that the user wants to play a game, a new Game Player is initialized. This Gameplayer will then create its environment and then create a new instance of a game engine to play a game. The environment created by the game player gives the user the power to switch between games, pause and play games, and save the state of the game. The game player will also be responsible for holding the high scores, which could be extended to outside of the platform within JavaFX (posting on Facebook, etc.) This class needs to be flexible in that it should be able to play any type of game, including our chosen genre and beyond. 
* The overall process of the game player will be: the user presses the "play game" option from the GUI/Display/Splash Screen. Then, a new instance of the game player will be created. There will be an option within the game player for the user to choose a game (most likely a combobox). When the user chooses something within the combobox and presses run, then the game player will initialize a game engine with the string from the combobox. The string gets passed to the gambe engine where the game is created. This game player will call various aspects of the external API of the game engine to interact with the properties of the game engine. It also controls the game engine on a very high level, where the games can be created, restarted, etc. 
* Within the game player, there will be some type of GUI with buttons and comboboxes and whatever other JavaFX element we need in order to create a flexible yet powerful environment for the user to play games in. The game player is essentially our "console" where anything beyond the specifics of a game can be controlled. 

Game Authoring Environment:
* The game authoring environment will be able to create and load new games that are comprised of GameObjects, GameScenes, Properties, and Behaviors. GameObjects are the objects that are placeable in the game, and they will have Properties and Behaviors (such as Health or Movement) via composition. GameObjects will be placed within GameScenes, which are essentially levels but could also be used for different functionalities (such as a custom splash screen). Each GameScene will have an ObjectManager that keeps track of all the GameObjects contained in that scene, and there will be a SceneManager for the entire game that keeps track of all the GameScenes. 
* Adding Properties and Behaviors to GameObjects using composition will give the user a lot of freedom to customize the objects in their game---for instance, they could make a simple block that has health and is able to shoot things but cannot move, or an enemy that is able to move but cannot shoot. Having managers for GameObjects and GameScenes allows the user to create many objects and levels that are stored within the things that need them.
   
Game Data:

* The Game Data module is responsible for containing all the data written by the Game Authoring Environment and save states of games played in the Game Engine. These data files will be stored in a standardized XML format, which will be produced when the Game Authoring Environment or Game Engine call the Serializer methods gameAuthorToXML or gameEngineToXML, respectively. In addition to these XML files and Serializer class, the Game Data will be responsible for holding all images which will be used within the program. The contained ImageManager class will return an ImageView through the method getImageView, which takes in a string parameter holding the location of the desired image.

* We chose to create the Game Data module to allow a communication means between the game authoring environment and the game engine. This way, the game authoring will not have to worry about keeping track of all the behaviors and properties that the game engine has to deal with when the game is played. It also keeps the game authoring separate from game engine, and provides methods for them to only access the data necessary for their operation.

Display: 
* The front end will primary be implemented with JavaFX display nodes. However, we still want this part of the project to be flexible and extendable to accommodate all possible user interactions that we might want to implement. Away to implement this is using abstract GUI buttons and use compositions and lambdas to deal with button event handling.

* An abstract GUI button will only have to contain the button label, size, and offset. In the constructor, you can pass in a ButtonEventHandler class that implements the event the button should do when pressed or interacted with. With this, we can use lambda expressions to simplify the worked required to create addtional buttons.

* Having lambdas and execution classes allows for flexible design to add new features for frontend interactions. Lambdas can take in the game engine or player as parameters, so they can have access to their public API and call their methods, which reduces the need for never large manager classes. Having display items as their own module keeps good encapsulation between frontend and backend between all the modules, and forces us to think about our public API and what other classes should be able to have access to. 
    
**Example Games** 

* Super Mario 
    Super Mario is a scrolling platformer with a large set of levels and maps. Each map contains levels with various types of environments, enemies and blocks. Within each level of Mario, there is a set end of the level, where if the player reaches, that level is beaten and the user can go to the next level. The movement of mario is fairly simple, which our program can easily implement - there is left and right movement with jumping. We also need an action key for any powerups we want. Since we can attach behaviors to various objects in a game, all we need do to to indicate the end of a level is attach the "end level" behavior to an object. Mario has lots of levels, as such, our authoring environment supports to creation of various levels within a single game. 
    
* Flappy Bird
    Flappy bird is an infinite scrolling platformer, has hazardous obstacles, and only requires one key event handler to flap the bird. These are easy to add to our game, as the game engine has event handlers, game authoring has visual ways of building the game environment, to make an infinite game, you just have to remove the boundaries, and make obstacles randomly generated. These can all be done within the game authoring environment by modifying GameObject behavior and properties, and therefore can be translated and supported by the game engine.
    
* Doodle Jump
  Doodle Jump is also a mobile infinite scrolling platformer. Unlike Flappy Bird, Doodle Jump scrolls vertically. To progress upwards, the player bounces off platforms. The game is over when the player falls to the bottom of the screen, or collides with an enemy character. The main player is controlled by tilting the mobile device left or right. Currently, we have only considered keyboard and mouse as input devices, so we would use arrow keys to control the character. Similar to Flappy Bird, by modifying GameObject behavior and properties within the game authoring environment, we can make the game more difficult as it progresses, randomly generating less platforms. 
    
**Design Considerations**  

* Game Player: We've discussed where to put a pause button and save state - either in the game player or game engine. Pausing can either be the same for all games, which means that the best use of pause would be in game player. However, we decided that we didn't want this extra dependency and it also inhibit the various different pause qualities across games, so we left it to the game engine. Saving the game could also be within the game engine, however, we thought that saving the game would be the same across all types of games, and that the game engine would hold some structure keeping track of all the objets. This data structure would get passed to the game player, which would then write out a XML file representing the game. This also makes sense because the save state button would be in the game player and the game engine has no idea of its existance. Overall, we will need to discuss the relationship between the game player and game engine in depth in order to separate them for flexibilities sake, but also keep them connect enough and allow them to communicate so that the game player can give the user the best experience for playing and maniupulating various games. 

* Game Data: When deciding how to store game information in a text format, we considered using both XML and JSON. Ultimately, while JSON would be less verbose and potentially more human-readable, we decided that we would use an XML format to store the majority of our game data. Given the existing support libraries for XML object storage, such as XStream, we felt as if this would be an easier format to work with. As another design consideration, we are currently operating under the assumption that both the Game Authoring Environment and Game Engine will utilize the same text file format. While this will make it much easier to pass information between the two modules, we need to make sure that we develop a robust system for storing game data in a manageable way. 

* Game Engine: We've discusses the heirarchy of parameters involved with each GameElement (behaviors, actions, properties, and events). We have yet to conclude how to pass all of the information as a Game State from one step to the next. We will need to discuss the seperation of responsibilies with the Game Engine (backend) and the display. In addition to that, we have to communicate with Game Data to discuss the   specific parameters that represent the basic implementation of the project in order to clear up communication issues. We also need to control meta information such as level control and transitions between levels and scenes. We need to map out the seperate tracks within the Game Engine (display, game flow, data/updating states).