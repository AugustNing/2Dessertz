package engine.behaviors;

import authoring.groovy.GroovyMethod;
import engine.GameElement;

public class Killer extends Behavior{
	private Double damagePower;
	
	public Killer(GameElement ge, Double damagePower) {
		super(ge);
		this.damagePower = damagePower;
	}
	
	public Killer (GameElement ge) {
		this(ge, 10.0);
	}
	
	@GroovyMethod
	public Double getDamagePower() {
		return damagePower;
	}
	
}
