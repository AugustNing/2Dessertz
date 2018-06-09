package engine.tests;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.GameScene;
import authoring.tests.EventTranslationTest;
import engine.GameElement;
import engine.GamePart;
import engine.authouringconversion.Converter2;
import engine.authouringconversion.Printer;
import engine.behaviors.Behavior;
import engine.behaviors.MandatoryBehavior;

class ConverterTester {
	GamePart testPart;
	Converter2 converter;
	ModelGamePart1 partMaker;
	Printer printer;
	
	@BeforeEach
	void setup() {
		partMaker = new ModelGamePart1();
		testPart = partMaker.getGamePart();
		converter = new Converter2();
		printer = new Printer();
	}
	
//	@Test
	void testBehaviorEng2Auth () {
		Behavior mand = partMaker.getMario().getBehavior(MandatoryBehavior.class);
		printAuthBehavior(converter.behavior2AuthBehavior(mand));
	}
	
	void printAuthBehavior(AuthBehavior authB) {
		//System.out.println("AuthBehavior: " + authB);
		authB.getProperties().stream() 
		.forEach(prop -> System.out.println(prop));
	}
	
	@Test
	void testGettingClasses() {
		
	}
//	@Test
	void testGameElement2GameObject () {
		GameElement mario = partMaker.getMario();
		printGameObject(converter.gameElement2GameObject(mario));
	}
	
//	@Test
	void testGameObject2GameElement() {
		GameObject obj =  new EventTranslationTest().getCharacter();
		GameElement ge = converter.gameObject2GameElement(obj);
		printer.printGameElement(ge);
	}
	
	void printGameObject (GameObject go) {
//		System.out.println("--------------------------");
//		System.out.println("GameObject toString: " + go);
//		System.out.println("Game Object Behaviors: " + go.getBehaviors() + " \n");
		for (AuthBehavior authB: go.getBehaviors()) {
			printAuthBehavior(authB);
			System.out.println();
		}
	}
	
	@Test
	void testGamePart2GameScene () {
		printer.printPart(testPart);
		printer.printScene(converter.gamePart2GameScene(testPart));
	}
	
	
	@Test
	GamePart testConvertAndBack () {
		GameScene scene = converter.gamePart2GameScene(testPart);
		GamePart s2 = converter.gameScene2GamePart(scene);
		compareParts(testPart, s2);
		return s2;
	}
	

	void compareParts(GamePart s1, GamePart s2) {
		List <GameElement> s1elems = s1.getElements();
		List <GameElement> s2elems = s2.getElements();
		
		System.out.println("State s1: ");
		for (GameElement s1elem: s1elems) {
			System.out.println(s1elem);
		}
		
		System.out.println("\nState s2: ");
		for (GameElement s2elem: s2elems) {
			System.out.println(s2elem);
		}
	}
	
	boolean testPartEquality(GamePart s1, GamePart s2) {
		List <GameElement> s1elems = s1.getElements();
		List <GameElement> s2elems = s2.getElements();
		if (s1elems.size() != s2elems.size()) {return false;}
		
		for (GameElement s1elem: s1elems) {
			if (!s2elems.contains(s1elem)) {
				return false;
			}
		}
		return true;

	}

}
