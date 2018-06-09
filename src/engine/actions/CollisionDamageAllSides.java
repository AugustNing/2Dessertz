package engine.actions;

import engine.GameElement;
import engine.behaviors.Killable;
import engine.behaviors.Killer;
import engine.events.elementevents.CollisionEvent;
import engine.events.elementevents.ElementEvent;

public class CollisionDamageAllSides implements Action {

	@Override
	public void act(ElementEvent e, GameElement ge) {
		if (e instanceof CollisionEvent) {
			CollisionEvent ce = (CollisionEvent)(e);
			GameElement other = ce.getOtherElement(ge);
			if (other.hasBehavior(Killable.class)) {
				Killable k = (Killable) other.getBehavior(Killable.class);
				Killer killer = (Killer) ge.getBehavior(Killer.class);
				k.reduceHealth(killer.getDamagePower());
			}
		}
		
	}

}
