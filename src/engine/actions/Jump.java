package engine.actions;

import engine.GameElement;
import engine.behaviors.MovableCharacter;
import engine.events.elementevents.ElementEvent;

public class Jump implements Action{

	@Override
	public void act(ElementEvent e, GameElement ge) {
		//System.out.println(1);
		//ArrayList<GameEvent> events = new ArrayList<GameEvent>();
		MovableCharacter mov = (MovableCharacter) ge.getBehavior(MovableCharacter.class);
		mov.jump();
	}
	
}
