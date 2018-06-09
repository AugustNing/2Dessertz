package data;

import authoring.GameObject;
import authoring.GameScene;
import engine.GamePart;
import authoring.GameSceneSerializable;
import engine.GameState;

import com.thoughtworks.xstream.XStream;
//
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Edward Zhuang
 * Class used to serialize game data through XStream.
 */
public class Serializer {

    private static final String SCENE = "scene";
    private static final String SAVE = "save";
    private XStream xstream;

    public Serializer() {
        xstream = new XStream();
    }

    /**
     * Converts game to XML. Used by game authoring environment.
     *
     * @param fileName  desired name of XML game file to be created
     * @param gameSceneList map provided from authoring environment which maps GameScene to a list of GameObjects contained on that scene
     */
    public void gameAuthorToXML(String fileName, List<GameSceneSerializable> gameSceneList) throws IOException {
        String topLevelGameDestination = fileName;
        new File(topLevelGameDestination).mkdirs();

        for (GameSceneSerializable aGameSceneList : gameSceneList) {
            String levelGameDestination = topLevelGameDestination + "/" + SCENE + gameSceneList.indexOf(aGameSceneList);
            String xmlString = xstream.toXML(aGameSceneList);
            stringToDom(xmlString, levelGameDestination + ".xml");
        }
    }
  

    // taken from https://stackoverflow.com/questions/17853541/java-how-to-convert-a-xml-string-into-an-xml-file
    private void stringToDom(String xmlSource, String fileName)
            throws IOException {
        java.io.FileWriter fw = new java.io.FileWriter(fileName);
        fw.write(xmlSource);
        fw.close();
    }

    /**
     * Saves GamePart
     * @param fileName
     * @param gamePart
     */
    public void savePartToXML(String fileName, GamePart gamePart) throws IOException {
    	int x = 1;
    	String topLevelGameDestination = fileName;
    	String xmlString = xstream.toXML(gamePart);
    	String levelGameDestination = topLevelGameDestination + "/" + SAVE;
        stringToDom(xmlString, levelGameDestination + ".xml");
    }

    /**
     * Saves Game Object
     * @param location location to save to
     * @param object GameObject to be saved
     * @param name name of xml file to be made
     * @throws IOException
     */
	public void saveGameObject(String location, GameObject object, String name) throws IOException {
		// TODO Auto-generated method stub
         String xmlString = xstream.toXML(object);
         stringToDom(xmlString, location + name  + ".xml");
	}
    

}


