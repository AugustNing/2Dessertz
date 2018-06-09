package authoring.display.controllers;

import java.util.List;

import authoring.GameObject;
import javafx.scene.image.Image;

/**
 * @author Edward Zhuang
 * Interface implemented by ObjectInfoController. Allows ObjectInfoBox to edit GameScene information.
 */
public interface ObjectInfoSaver {
	List<GameObject> getAllGameObjects();
	void duplicateGameObject();
	Image getImage(String imageName);
	void updatePositions();
	void setImage(Image image, String imageName);
}
