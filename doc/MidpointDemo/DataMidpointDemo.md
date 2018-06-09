## Data midpoint demo


### High level design
* Goal is to allow Game Authoring to save their game templates to XML
* Allow Game Authoring and Game Engine to load these XML game templates.
* Allow Game Engine to save and load save states to and from a save XML file. 
* Pass information about Games for Game Player GUI
* Keep Game Authoring and Game Engine separate, so there is no need for them to pass data between each other.
* Help with data access, ex images. 
* Manage data file hierarchy/organization.


## Image Manager API

This util is used to create JavaFX images from the string name of the image within a specific game, used in game authoring and game engine. It manages filepath and accessing the images.

```java 
public class ImageManager {

    /**
    * Constructor
    * @param gameName name of game to load and store images from
    */
    public ImageManager(String gameName){}


    /**
     * Retrieves an FX Image from images folder of a game
     * @param imageName name of image desired
     * @return Java FX Image
     */
    public Image getImage(String imageName){}

    /**
     * Stores a Java FX Image into game's image folder as BufferedImage
     * @param imageName desired name of image
     * @param image to be stored
     */
    public void storeImage(String imageName, Image image){}


}


```
## Images
This is how a serialized Game Authoring file (GameScene class) looks like.

![XML file format](XML.PNG)

This is how a serialized Game Engine file (GameState class) looks like. This XML file can easily be over 1000 lines long.

![Serialized save file format](SaveXML.PNG)

The Data filepath hiearchy. Each game corresponds to its own named folder, and each game will have descriptions, images, saves, and scenes, which is used by authoring, engine, and playing.

![Data file hierarchy](Directory.PNG)

## Notes
Design is limited by XStream since we are still trying to redefine how we want to structure the game files, so it is not very closed, but flexible, as it is very easy to extend for new requirement from and add new serializers/managers for new desired functionality. 

GameData has no information about front end implementation, as we tried to isolate the functionality of this component as much as possible.


XStream cannot serialize JavaFX objects, so all other sub-teams have to design classes around this limitation.
* For each type of item that is to be serialized, we must create new methods within Gamesaver, Serializer, Deserializer, and Gameloader. This is important to note, since game data is built upon agreed naming conventions within the file hierarchy which must be expanded upon when we make game files more 
complex. 

Tradeoffs:

Currently, we do not have the capability to serialize any type of object. At the same time, however, this allows us to exercise more advanced error checking and helps us maintain a uniform game data structure.
* throw informative errors for front end notifications
* dynamic saving for every time step
* help with standardizing object save format

In our current model, the Serializer class is isolated from the other game components, and is only accessed through another game data class Gamesaver. As a result, this gives the Game Engine and Game Authoring much less freedom in how they can save their data, but we felt as it encapsulating the Serializer within the Gamesaver helped limit the implementation details in a way that would be more beneficial overall. It also reduces the amount of filepath management they need to keep track of.