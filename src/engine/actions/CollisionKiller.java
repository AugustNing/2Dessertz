package engine.actions;

import engine.GameElement;
import engine.behaviors.Killable;
import engine.behaviors.Killer;
import engine.events.elementevents.CollisionEvent;
import engine.events.elementevents.ElementEvent;
import engine.events.gameevents.RemoveGameElementEvent;

public class CollisionKiller implements Action {

	@Override
	public void act(ElementEvent e, GameElement ge) {
		CollisionEvent ce = (CollisionEvent) e;
		GameElement other = ce.getOtherElement(ge);
		if (other.hasBehavior(Killable.class)) {
			Killable k = (Killable) other.getBehavior(Killable.class);
			k.reduceHealth(((Killer) ge.getBehavior(Killer.class)).getDamagePower());
			System.out.println("Health Reduced to: " + k.getHealth());
			ge.addGameEvent(new RemoveGameElementEvent(ge));
		}
		
	}

}
