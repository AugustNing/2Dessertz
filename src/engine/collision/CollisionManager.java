package engine.collision;

import java.util.ArrayList;
import java.util.List;

import engine.GameElement;
import engine.GamePart;
import engine.behaviors.MandatoryBehavior;
import engine.events.elementevents.CollisionEvent;
import engine.events.gameevents.GameEvent;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

/**
 * @author Martin
 * 
 * Create a new Collision Manager, used to search for and act on any collisions found between two active
 * game elements in the current GamePart.
 *
 */
public class CollisionManager {
	
	/**
	 * Loops through each distinct pair of GameElements in the given GamePart, and determines if a rough collision
	 * exists between any two GameParts. If there is a collision, a ElementEvent containing the colliding GameElements
	 * is created and passed to the two GameElements to be processed. The GameEvents returned by this process are 
	 * consolidated and returned by this method.
	 * 
	 * @param gamePart 	Current GamePart to be analyzed for collisions
	 * @return 	List of GameEvents returned by the GameElements in the GamePart
	 */
	public List<GameEvent> handleCollisions(GamePart gamePart) {
		List<GameEvent> returnEvents = new ArrayList<>();
		for (int i = 0; i < gamePart.getElements().size(); i++) {
			MandatoryBehavior a = (MandatoryBehavior) gamePart.getElements().get(i).getBehavior(MandatoryBehavior.class);
			
			for (int j = i+1; j < gamePart.getElements().size(); j++) {
				MandatoryBehavior b = (MandatoryBehavior) gamePart.getElements().get(j).getBehavior(MandatoryBehavior.class);
				if (a.getShape().getBoundsInLocal().intersects(b.getShape().getBoundsInLocal())) {
					Shape intersect = Shape.intersect(a.getShape(), b.getShape());
					if (((Path) intersect).getElements().size() != 0) {
						GameElement g1 = gamePart.getElements().get(i);
						GameElement g2 = gamePart.getElements().get(j);
						CollisionEvent collision = new CollisionEvent(g1, g2);
						returnEvents.addAll(g1.processEvent(collision));
						returnEvents.addAll(g2.processEvent(collision));
					}
				}
			}
		}
		return returnEvents;
	}
	
}
