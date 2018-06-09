package engine.events.elementevents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import engine.GameElement;
import engine.behaviors.MandatoryBehavior;
import javafx.scene.shape.Shape;

/**
 * @author Martin
 *
 * Collision events are used to communicate collisions between two GameElements. They compute the exact side
 * of the collision with respect to each element, and give this information to be processed by a GameElement's
 * event responder.
 */
public class CollisionEvent extends ElementEvent {
	
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String TOP = "TOP";
	public static final String BOTTOM = "BOTTOM";
	public static final List<String> ALL_SIDES = Arrays.asList(LEFT, RIGHT, TOP, BOTTOM);
	public static final List<String> SIDES = Arrays.asList(LEFT, RIGHT);
	public static final List<String> VERTICALS = Arrays.asList(TOP, BOTTOM);

	
	private GameElement e1;
	private GameElement e2;
	private List<String> e1side;
	private List<String> e2side;
	
	/**
	 * Create a new CollisionEvent
	 * 
	 * @param elem1 Collided GameElement1
	 * @param sides1 Significant collision sides of GameElement1 
	 * @param elem2 Collided GameElement2
	 * @param sides2 Significant collision sides of GameElement2
	 */
	public CollisionEvent(GameElement elem1, List<String> sides1, GameElement elem2, List<String> sides2) {
		e1 = elem1;
		e2 = elem2;
		e1side = new ArrayList<>();
		e2side = new ArrayList<>();
		e1side.addAll(sides1);
		e2side.addAll(sides2);
	}
	
	/**
	 * Create a new CollisionEvent
	 * 
	 * @param elem1 Collided GameElement1
	 * @param elem2 Collided GameElement2
	 */
	public CollisionEvent(GameElement elem1, GameElement elem2) {
		e1 = elem1;
		e2 = elem2;
		e1side = new ArrayList<String>();
		e2side = new ArrayList<String>();
		processCollisionSides();
	}
	
	/**
	 * Returns the GameElement involved in the collision that is not the one given.
	 * 
	 * @param notThisOne GameElement of one of the collision elements
	 * @return	the other GameElement
	 */
	public GameElement getOtherElement(GameElement notThisOne) {
		if (e1.equals(notThisOne)) {
			return e2;
		}
		return e1;
	}
	
	private void processCollisionSides() {
		MandatoryBehavior mand1 = (MandatoryBehavior) e1.getBehavior(MandatoryBehavior.class);
		MandatoryBehavior mand2 = (MandatoryBehavior) e2.getBehavior(MandatoryBehavior.class);
		Shape s1 = mand1.getShape();
		Shape s2 = mand2.getShape();
		Shape intersection = Shape.intersect(s1, s2);
		e1side.add(getCollisionSide(s1, intersection));
		e2side.add(getCollisionSide(s2, intersection));
	}
	
	private String getCollisionSide(Shape elementShape, Shape intersection) {
		List<Double> centerElement = getCenter(elementShape);
		List<Double> centerIntersect = getCenter(intersection);
		List<Double> diffVector = Arrays.asList(centerIntersect.get(0) - centerElement.get(0), centerIntersect.get(1) - centerElement.get(1));
		if (Math.abs(diffVector.get(1)) > Math.abs(diffVector.get(0))) {
			if (diffVector.get(1) < 0) {
				return BOTTOM;
			} else {
				return TOP;
			} 
		} 
		else {
			if (diffVector.get(0) < 0) {
				return RIGHT;
			} else {
				return LEFT;
			}
		}
	}
	
	private List<Double> getCenter(Shape s) {
		List<Double> ret = new ArrayList<Double>();
		ret.add((s.getBoundsInLocal().getMaxX() + s.getBoundsInLocal().getMinX())/2);
		ret.add((s.getBoundsInLocal().getMaxY() + s.getBoundsInLocal().getMinY())/2);
		return ret;
	}

	private List<GameElement> getCollidedElements() {
		List<GameElement> ret = new ArrayList<>();
		ret.add(e1); ret.add(e2);
		return ret;
	}
	
	@Override
	public String toString() {
		return "Collision Event: " + e1 + " " + e2 + " collided.";
	}
	
	/**
	 * Determine whether a GameElement is contained within the collision represented
	 * by this event.
	 * @param el GameElement of question
	 * @return 	whether this GameElement is involved in the collision
	 */
	public boolean containsElement(GameElement el) {
		return getCollidedElements().contains(el);
	}
	
	/**
	 * Checks if another Collision matches this one as a trigger
	 * For a collision this means:
	 * other has this.e1 as one of the elements that collided
	 * the element that is not e1 in other.collidedElements() is has 
	 * the same identifier as this.e2
	 * 
	 */
	@Override
	public boolean matchesEvent(ElementEvent incomingEvent) {
		if (incomingEvent instanceof CollisionEvent) {
			CollisionEvent incomingCollision = (CollisionEvent) incomingEvent;
			if (incomingCollision.containsElement(e1)) {
				GameElement incomingOther = incomingCollision.getOtherElement(e1);
				//System.out.println("In Collision Event matching: " + incomingEvent);
				if (incomingOther.matchesType(e2)) {
					String incomingOtherSide = incomingCollision.getSidesForElement(incomingOther).get(0);
					String incomingThisSide = incomingCollision.getSidesForElement(e1).get(0);
					if (e1side.contains(incomingThisSide) && e2side.contains(incomingOtherSide)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private List<String> getSidesForElement(GameElement ge) {
		if (ge == e1) {
			return e1side;
		} else if (ge == e2) {
			return e2side;
		}
		return null;
	}
	@Override
	public String getTriggerString() {
		return e2.getIdentifier();
	}
	
}
