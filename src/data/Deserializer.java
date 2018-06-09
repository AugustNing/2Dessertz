package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.thoughtworks.xstream.XStream;

import authoring.GameObject;
import authoring.GameSceneSerializable;
import authoring.display.popups.ErrorBox;
import engine.GamePart;

/**
 * @author Edward Zhuang
 * Class used to deserialize XML files. Used by the GameLoader class.
 */
public class Deserializer {

    private static final String DS_STORE = "DS_Store";
    private XStream xstream;
    private static final String SAVE = "save";
    
    public Deserializer(){
        xstream = new XStream();
    }

    /**
     * Gets a list of GameSceneSerializables from xml files.
     * @param fileName name of file;
     * @return GameScene
     */
    public List<GameSceneSerializable> getGameSceneSerializables(String fileName){
        File directory = new File(fileName);
        File[] directoryListing = directory.listFiles();
        List<GameSceneSerializable> list = new ArrayList<>();
        if (directoryListing != null){
            for (File level : directoryListing){
                String path = level.getAbsolutePath();
                if (path.contains(DS_STORE)) {continue;}
                list.add(retrieveGameSceneSerializable(path));
            }
        }
        return list;
    }

    /**
     * Gets a GameObject from xml file
     * @param fileName name of file
     * @return GameObject
     */
    public GameObject getGameObject(String fileName) {
    	 File file = new File(fileName);
         return (GameObject) xstream.fromXML(convertXMLFileToString(file));
    }

    private GameSceneSerializable retrieveGameSceneSerializable(String fileName) {
        File file = new File(fileName);
        return (GameSceneSerializable) xstream.fromXML(convertXMLFileToString(file));
    }

    /**
     * Gets a GamePart from xml file
     * @param fileName name of file
     * @return GamePart
     */
    public GamePart getSavePart(String fileName) {
    	String saveState = fileName + "/" + SAVE + ".xml";
    	File file = new File(saveState);
        return (GamePart) xstream.fromXML(convertXMLFileToString(file));
    }

    /**
     * converts a file to string. taken from http://techdiary.bitourea.com/2008/07/convert-xml-file-to-xml-string-in-java.html
     * @param file desired xml file
     * @return string of xml
     */
    private String convertXMLFileToString(File file)
    {
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            InputStream inputStream = new FileInputStream(file);
            org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
            StringWriter stw = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(doc), new StreamResult(stw));
            return stw.toString();
        }
        catch (Exception e) {
        	new ErrorBox("Error Deserializing", "XML file could not be deserialized");
        }
        return null;
    }
}
