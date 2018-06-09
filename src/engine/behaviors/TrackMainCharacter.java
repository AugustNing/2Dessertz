package engine.behaviors;

import engine.GameElement;

public class TrackMainCharacter extends Behavior{
	
	private GameElement trackedElement;
	
	public TrackMainCharacter(GameElement ge, GameElement trackedElement) {
		super(ge);
		this.trackedElement = trackedElement;
	}
	
	public Double getTrackedX() {
		return trackedElement.getMandatoryBehavior().getX();
	}
	
	public Double getTrackedY() {
		return trackedElement.getMandatoryBehavior().getY();
	}
}
