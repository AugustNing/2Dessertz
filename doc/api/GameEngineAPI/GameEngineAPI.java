/* Classes that implement GameElement represent concrete objects within the game. They accept
 * ElementEvents in order to use its behaviors manage its properties. They also return 
 */
public interface GameElement {
	public GameEvent update(GameEvent ElementEvent);
	public void setBehavior(Behavior behavior);
}

/* Classes that implement Behavior include code that edit the properties associated with
 * this behavior given a gameEvent. 
 */
public interface Behavior {
	public Behavior(GameElement ge);
	public Map<String, int> getProperty();
}

/* Classes that implement ElementEvent are communication tools from the EventManager to the
 * GameElements and Behaviors.
 */
public interface ElementEvent {
	public void execute();
}

/* Classes that implement GameEvent are communication tools between the GameElements and
 * the EventManager.
 */
public interface GameEvent {
	public GameState execute(GameState gameState);
}

/* The event manager is in charge of managing the game state. It includes methods that
 * are either event listeners or methods that are called in each game step. Each method
 * will loop throught the elements within the gameState and pass in specific GameEvent
 * objects to the elements' update method. This returns a new GameEvent which is handled
 * by the handleElementEvents method.
 */
public interface EventManager {
	public void detectCollisions(GameState gameState);
	public void step(GameState gameState);
	public void keyEvent(GameState gameState);
	public void mouseEvent(GameState gameState);
	public void handleElementEvents(GameState gameState, GameEvent gameEvent);
}


/* The game state is an information-holding object which contains the states of all 
 * GameElements that currently exist.
 */
public interface GameState implements Iterable {
	public void addGameElement(GameElement gameElement);
	public void removeGameElement(String name);
	public List<Map<String, Integer>> getDisplayStates();
}

public interface Engine {
	public void keyPressEvent();
	public void mousePressEvent();
	public void start(Stage stage);
}
