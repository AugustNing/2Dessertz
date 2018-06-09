package data;

import authoring.GameObject;
import authoring.GameScene;
import authoring.GameSceneSerializable;
import data.propertiesFiles.ResourceBundleManager;
import engine.GamePart;
import engine.exceptions.ErrorBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Edward Zhuang
 * This class is responsible for saving games from both the authoring and engine environment
 */
public class GameSaver {

	public static final String BASELOCATION = ResourceBundleManager.getPath("BASELOCATION");
	private static final String DESCRIPTION = ResourceBundleManager.getPath("DESCRIPTION");
	private static final String SCENES = ResourceBundleManager.getPath("SCENES");
	private static final String SAVES = ResourceBundleManager.getPath("SAVES");
	private static final String NAME = ResourceBundleManager.getPath("NAME");
	private static final String DESCRIPTIONTEXT = ResourceBundleManager.getPath("DESCRIPTIONTEXT");
	private static final String DEFAULTIMAGE = ResourceBundleManager.getPath("DEFAULTIMAGE");
	private static final String DESCRIPTIONIMAGE = ResourceBundleManager.getPath("DESCRIPTIONIMAGE");
	private Serializer serializer;
	private String gameDescriptionLocation;
	private String gameScenesLocation;
	private String gameSavesLocation;

	/**
	 * GameSaver constructor
	 * @param gameName desired name of game root file
	 */
	public GameSaver(String gameName) {
		String gameLocation = BASELOCATION + gameName + "/";
		gameDescriptionLocation = gameLocation + DESCRIPTION;
		gameScenesLocation = gameLocation + SCENES;
		gameSavesLocation = gameLocation + SAVES;
		serializer = new Serializer();

	}

	/**
	 * provides a description of the game. Adds logo as game description image
	 * @param name name of game
	 * @param description description of game
	 */
	public void addDescription(String name, String description) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(gameDescriptionLocation + NAME)) {
			out.println(name);
		}
		try (PrintWriter out = new PrintWriter(
				gameDescriptionLocation + DESCRIPTIONTEXT)) {
			out.println(description);
		}

		try {
			BufferedImage img = ImageIO.read(new File(DEFAULTIMAGE));
			ImageIO.write(img, "jpg",
					new File(gameDescriptionLocation + DESCRIPTIONIMAGE));
		} catch (IOException e) {
			new ErrorBox("Image Not Found", "Could not access default image");
		}

	}

	/**
	 * Saves the game that authoring environment has created. An xml file is created
	 * for each scene.
	 */
	public void gameAuthorToXML(List<GameScene> gameSceneList, boolean isAuthoring) throws IOException {
		List<GameSceneSerializable> serializables = new ArrayList<>();
		for (GameScene scene : gameSceneList) {
			serializables.add(new GameSceneSerializable(scene));
		}
		if (isAuthoring) {
			serializer.gameAuthorToXML(gameScenesLocation, serializables);
		} else {
			serializer.gameAuthorToXML(gameSavesLocation, serializables);
		}
	}

	/**
	 * Saves the game when the game is being played in game engine
	 * @throws IOException
	 */
	public void saveGamePart(GamePart gamePart) throws IOException {
		serializer.savePartToXML(gameSavesLocation, gamePart);
	}

}
