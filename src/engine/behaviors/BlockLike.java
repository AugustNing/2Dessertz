package engine.behaviors;

import engine.GameElement;
import engine.actions.CollisionStopXMotion;
import engine.actions.CollisionStopYMotion;
import engine.events.elementevents.CollisionEvent;

public class BlockLike extends Behavior {

	public BlockLike(GameElement ge) {
		super(ge);
	}
	
	@Override
	protected void addDefaultBehavior() {
		getParent().addEventResponse(new CollisionEvent(getParent(), CollisionEvent.VERTICALS, new GameElement(MandatoryBehavior.REFER_ALL_ELEMENTS), CollisionEvent.VERTICALS), 
				(event, ge) -> {
					CollisionEvent ce = (CollisionEvent) event;
					if (!(ce.getOtherElement(ge).hasBehavior(IgnoresBlocks.class))) {
						new CollisionStopYMotion().act(event, ge);
					}
				});
		getParent().addEventResponse(new CollisionEvent(getParent(), CollisionEvent.SIDES, new GameElement(MandatoryBehavior.REFER_ALL_ELEMENTS), CollisionEvent.SIDES), 
				(event, ge) -> {
					CollisionEvent ce = (CollisionEvent) event;
					if (!(ce.getOtherElement(ge).hasBehavior(IgnoresBlocks.class))) {
						new CollisionStopXMotion().act(event, ge);
					}
				});
	}
	
	

}
