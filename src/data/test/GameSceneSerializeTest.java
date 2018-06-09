package data.test;

import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import authoring.GameObject;
import authoring.GameScene;
import authoring.GameSceneSerializable;
import engine.actions.GroovyAction;
import engine.authouringconversion.Converter2;
import engine.authouringconversion.Printer;
import engine.events.elementevents.KeyInputEvent;
import engine.tests.ModelGamePart1;
import engine.tests.ModelGamePart2;
import javafx.scene.input.KeyCode;

public class GameSceneSerializeTest {
	
	
	
	public static void main (String[] args) throws IOException {
		Converter2 converter = new Converter2();
		ModelGamePart1 mpm = new ModelGamePart1();
		XStream xstream = new XStream();
		GameScene modelGamePart1Scene = converter.gamePart2GameScene(mpm.getGamePart());
		GameSceneSerializable serializable = new GameSceneSerializable(modelGamePart1Scene);
		String levelGameDestination = "./data/gamedata/games/enginetestmario/scenes/testscene";
		
		Printer printer = new Printer();
		printer.printScene(modelGamePart1Scene);
		
//		GameObject block = converter.gameElement2GameObject(mp1.getMario());
//		String inputEvent = xstream.toXML(new KeyInputEvent(KeyCode.P));
//		String blockString = xstream.toXML(block);
//		String respString = xstream.toXML(new GroovyAction("EXECUTIONER"));
		String xmlString = xstream.toXML(serializable);
		java.io.FileWriter fw = new java.io.FileWriter(levelGameDestination + ".xml");
        fw.write(xmlString);
        fw.close();
	}
}

