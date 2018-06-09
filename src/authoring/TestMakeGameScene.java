package authoring;

import java.io.IOException;
import java.util.ArrayList;

import data.*;
import engine.tests.ModelGameState;
import javafx.scene.image.Image;

public class TestMakeGameScene {

	public TestMakeGameScene() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws IOException {
//		
//		GameScene gameScene = new GameScene("Level 1");
//		BehaviorFactory bf = new BehaviorFactory();
//		GameObject go1 = new GameObject(bf.makeBehavior("MandatoryBehavior"));
//		go1.getBehavior("MandatoryBehavior").getProperty("xPos").setValue(new Double(2));
//		go1.getBehavior("MandatoryBehavior").getProperty("yPos").setValue(new Double(7));
//		go1.getBehavior("MandatoryBehavior").getProperty("elementName").setValue("this is my name");
//
////		GameObject go2 = new GameObject(bf.makeBehavior("MandatoryBehavior"));
////		go2.addBehavior(bf.makeBehavior("Movable"));
//		System.out.println(go1.getBehaviors());
////		System.out.println(go2.getBehaviors());
//
//		gameScene.addObject(go1);
//		
//		ArrayList<GameScene> gameList = new ArrayList<>();
//		gameList.add(gameScene);
//		
//		GameInitializer gameInitializer = new GameInitializer("gouttham");
//		GameSaver gameSaver = new GameSaver("gouttham");
//		gameSaver.addDescription("HELLO", "THIS IS A TEST GAME.", null);
//		gameSaver.gameAuthorToXML(gameList);
//		
//		GameLoader gameLoader = new GameLoader("gouttham");
//		GameScene pls = gameLoader.getGameScenes().get(0);
//		System.out.println(pls.getMyObjects().get(0).getBehaviors());
		
		GameSaver gameSaver = new GameSaver("enginetestmario");
		ModelGameState model = new ModelGameState();
		gameSaver.saveGamePart(model.getPart());
		
		
		
	}

}
