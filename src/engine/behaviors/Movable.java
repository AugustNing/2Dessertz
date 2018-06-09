package engine.behaviors;

import java.util.Arrays;
import java.util.List;

import authoring.groovy.GroovyMethod;
import engine.GameElement;
import engine.actions.TimeMovable;
import engine.events.elementevents.TimeEvent;

public class Movable extends Behavior{
	
	public static final int X_LIST_POS = 0; // Position of X in direction 
	public static final int Y_LIST_POS = 1; // Position of Y in direction
	
	private Double xVel;
	private Double yVel;	
	
	public Movable(GameElement ge, Double vel, List<Double> dir) {
		super(ge);
		xVel = 0.0;
		yVel = 1.0;
		setVelocity(vel);
		if (dir.stream().reduce(0.0, (a, b) -> a + Math.abs(b)) != 0) {
			setDirection(dir);
		} else {
			throw new IllegalArgumentException("Invalid Direction for " + ge.getIdentifier() + ": " + dir);
		}
	}
	
	
	public Movable (GameElement ge, Double xv, Double yv) {
		super(ge);
		xVel = xv; 
		yVel = yv;
	}
	
	public Movable(GameElement ge) {
		this(ge, 5.0, Arrays.asList(0.0, 1.0));
	}
	
	/*
	 * Moves the parent game element according to the time amount requested
	 */
	@GroovyMethod
	public void move(Double time) {
		MandatoryBehavior bge = (MandatoryBehavior) getParent().getBehavior(MandatoryBehavior.class);
		bge.setPosition(bge.getX() + xVel * time, bge.getY() + yVel * time);
	}
	
	
	/*
	 * Sets the direction for this element. Checks if the direction 
	 * is valid and normalizes it so move method works correctly
	 */
	@GroovyMethod
	public void setDirection(List<Double> dir) {
		Double v = getVelocity();
		setXVelocity(v * dir.get(0));
		setYVelocity(v * dir.get(1));
	}
	
	/*
	 * Sets the velocity according to what the other classes want. Checks if velocity is valid for this object 
	 */
	@GroovyMethod
	public void setVelocity (Double v) {
		xVel = getDirection().get(0) * v;
		yVel = getDirection().get(1) * v;
	}
	
	@GroovyMethod
	public void setXVelocity (Double xv) {
		xVel = xv;
	}
	
	@GroovyMethod
	public void setYVelocity (Double yv) {
		yVel = yv;
	}
	
	@GroovyMethod
	public Double getXVelocity () {
		return xVel;
	}
	
	@GroovyMethod
	public Double getYVelocity () {
		return yVel;
	}

	@GroovyMethod
	public Double getVelocity () {
		return Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
	}
	
	@GroovyMethod
	public List<Double> getDirection() {
		return Arrays.asList(xVel / getVelocity(), yVel /getVelocity());
	}
	
	@Override
	protected void addDefaultBehavior() {
		getParent().addEventResponse(new TimeEvent(0.0), new TimeMovable());
	}
}
