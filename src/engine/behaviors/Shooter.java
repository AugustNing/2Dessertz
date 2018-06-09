package engine.behaviors;

import java.util.Arrays;
import java.util.List;

import authoring.GameObject;
import authoring.groovy.GroovyMethod;
import engine.GameElement;
import engine.authouringconversion.Converter2;
import engine.authouringconversion.Printer;
import engine.events.gameevents.AddElementEvent;

public class Shooter extends Behavior {
	
	private GameObject toShoot;
	private Double defaultVelocity;
	private Double distAway; // The distance away from the character the bullet is generated 
	
	public Shooter(GameElement ge, Double defaultVel, Double dist) {
		super(ge);
		defaultVelocity = defaultVel;
		distAway = dist;
		toShoot = new GameObject();
	}
	
	public Shooter(GameElement ge) {
		this(ge, 30.0, 10.0);
		MandatoryBehavior mand = (MandatoryBehavior) ge.getBehavior(MandatoryBehavior.class);
		distAway = Math.sqrt(Math.pow(mand.getShape().getBoundsInLocal().getHeight(), 2.0) + Math.pow(mand.getShape().getBoundsInLocal().getWidth(), 2.0));
	}
	
	@GroovyMethod
	public void shoot(Double v, List<Double> direction) {
		MandatoryBehavior mand = (MandatoryBehavior) getParent().getBehavior(MandatoryBehavior.class);
		Double magDirection = Math.sqrt(Math.pow(direction.get(0), 2) + Math.pow(direction.get(1), 2));
		Double startx = mand.getX() + distAway * direction.get(0)/magDirection + mand.getShape().getBoundsInLocal().getWidth()/2;
		Double starty = mand.getY() + distAway * direction.get(1)/magDirection + mand.getShape().getBoundsInLocal().getHeight()/2;
//		GameElement bullet = new ModelGamePart1().getBullet(startx, starty, v, direction);
//		System.out.println("Shooter toShoot: " + toShoot);
//		new Printer().printGameObject(toShoot);
//		System.out.println("Shooter GE hashcode: " + getParent().hashCode());
		toShoot.setxPos(startx);
		toShoot.setyPos(starty);
		GameElement bullet = new Converter2().gameObject2GameElement(toShoot);
		if (bullet.hasBehavior(Movable.class)) {
			Movable m = (Movable)bullet.getBehavior(Movable.class);
			m.setDirection(direction);
			m.setVelocity(v);
		}
		new Printer().printGameElement(bullet);
		getParent().addGameEvent(new AddElementEvent(bullet));
	}
	
	@GroovyMethod
	public void shoot(List<Double> direction) {
		shoot(defaultVelocity, direction);
	}
	
	@GroovyMethod
	public void shootRight() {
		shoot(defaultVelocity, Arrays.asList(1.0, 0.0));
	}
	
	@GroovyMethod
	public void shootLeft() {
		shoot(defaultVelocity, Arrays.asList(-1.0, 0.0));
	}
	
	@GroovyMethod
	public void shootUp() {
		shoot(defaultVelocity, Arrays.asList(0.0, 1.0));
	}
	
	@GroovyMethod
	public void shootDown() {
		shoot(defaultVelocity, Arrays.asList(0.0, -1.0));
	}
}
