package engine.actions;

import engine.GameElement;
import engine.behaviors.EntrancePortal;
import engine.events.elementevents.ElementEvent;

public class ChangeLevel implements Action{

	@Override
	public void act(ElementEvent e, GameElement ge) {
		EntrancePortal p = (EntrancePortal) ge.getBehavior(EntrancePortal.class);
		p.changeLevel();
		//p.runPortal();
	}
	
}
