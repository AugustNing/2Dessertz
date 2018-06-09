package authoring;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @author Edward Zhuang
 * Interface implemented by GameScene, letting GameViewWindow access some information.
 */
public interface GameViewObservable {

	List<GameObject> getMyObjects();
	List<SceneBackgroundImageSerializable> getBackgroundImageSerializables();
}
