package engine.actions;

import java.util.Arrays;
import java.util.List;

import engine.GameElement;
import engine.behaviors.MandatoryBehavior;
import engine.behaviors.Movable;
import engine.events.elementevents.CollisionEvent;
import engine.events.elementevents.ElementEvent;
import javafx.scene.shape.Shape;

public class CollisionStopYMotion implements Action{

	@Override
	public void act(ElementEvent e, GameElement thisElem) {
		CollisionEvent ce = (CollisionEvent) e;
		GameElement ge = ce.getOtherElement(thisElem);
		if (ge.hasBehavior(Movable.class)) {
			Movable movable = (Movable) ge.getBehavior(Movable.class);
			movable.setYVelocity(0.0);
		}
		separateElements(ge, thisElem);
	}
	
	private void separateElements(GameElement e1, GameElement e2) {
		MandatoryBehavior mand1 = (MandatoryBehavior) e1.getBehavior(MandatoryBehavior.class);
		MandatoryBehavior mand2 = (MandatoryBehavior) e2.getBehavior(MandatoryBehavior.class);
		Shape s1 = mand1.getShape();
		Shape s2 = mand2.getShape();
		if (e1.hasBehavior(Movable.class)) {
			moveElementOutOfContact(e1, Shape.intersect(s1, s2));
		} else if (e2.hasBehavior(Movable.class)){
			moveElementOutOfContact(e2, Shape.intersect(s1, s2));
		}

	}
	
	private void moveElementOutOfContact(GameElement ge, Shape intersection) {
		MandatoryBehavior mand = (MandatoryBehavior) ge.getBehavior(MandatoryBehavior.class);
		if (getCenter(mand.getShape()).get(1) < getCenter(intersection).get(1)) {
			mand.setPosition(mand.getX(), mand.getY() - intersection.getBoundsInLocal().getHeight());
		} else {
			mand.setPosition(mand.getX(), mand.getY() + intersection.getBoundsInLocal().getHeight());
		}
	}
	
	private List<Double> getCenter(Shape s) {
		return Arrays.asList((s.getBoundsInLocal().getMaxX() + s.getBoundsInLocal().getMinX())/2,
				(s.getBoundsInLocal().getMaxY() + s.getBoundsInLocal().getMinY())/2);
	}

}
