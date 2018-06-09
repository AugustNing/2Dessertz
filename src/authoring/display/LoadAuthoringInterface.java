package authoring.display;

import authoring.Game;

/**
 * @author Edward Zhuang
 * Interface implemented by AuthoringDisplay which is passed into popups allowing them to
 * create games.
 */
public interface LoadAuthoringInterface {
	void loadAuthoringEnvironment(Game game);
}
