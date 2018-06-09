package engine.behaviors;
import java.util.Arrays;

import authoring.groovy.GroovyMethod;
import engine.GameElement;
import engine.actions.TimeGravity;
import engine.events.elementevents.TimeEvent;

/**
 * @author Gouttham 
 * The Game Elements which acquires this property have the ability to inherit event responses that allow it to respond to gravity. 
 *
 */
public class Gravity extends Behavior{
	public static final Double GRAVITATIONAL_FORCE_DEFAULT = 9.8*10;
	
	private Double gravityForce;
	
	public Gravity(GameElement ge) {
		super(ge);
		gravityForce = GRAVITATIONAL_FORCE_DEFAULT;
	}
	
	@GroovyMethod
	public void experienceGravity(Double time) {
		Movable b = (Movable) getParent().getBehavior(Movable.class);
		b.setYVelocity(((Movable) getParent().getBehavior(Movable.class)).getYVelocity()+(gravityForce*time));
	}
	
	@GroovyMethod
	public void reverseGravity(Double time) {
		Movable b = ((Movable) getParent().getBehavior(Movable.class));
		(b).setYVelocity(b.getYVelocity()-(gravityForce*time));
	}
	
	@GroovyMethod
	public void setGravity(Double g) {
		
	}
	
	@Override
	protected void addDefaultBehavior() {
		this.getParent().addEventResponse(new TimeEvent(0.0), new TimeGravity());
	}
	
	@Override
	protected void addRequiredBehaviors() {
		addBehaviorsIfNotExisting(Arrays.asList(Movable.class));
	}
	


}
