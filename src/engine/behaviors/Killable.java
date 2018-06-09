package engine.behaviors;

import authoring.groovy.GroovyMethod;
import engine.GameElement;
import engine.events.gameevents.GameOverEvent;
import engine.events.gameevents.RemoveGameElementEvent;

public class Killable extends Behavior{
	private Double health;
	private Double decayRate;
	private Double default_health;
	
	public Killable(GameElement ge, Double health, Double decayRate, Double lifeHealth) {
		super(ge);
		this.health = health;
		this.decayRate = decayRate;
		default_health = lifeHealth;
	}
	
	public Killable(GameElement ge, Double health, Double decayRate) {
		this(ge, health, decayRate, 10.0);
	}
	
	public Killable(GameElement ge, Double health) {
		this(ge, health, 0.0);
	}
	
	public Killable(GameElement ge) {
		this(ge, 30.0, 0.0);
	}
	
	@GroovyMethod
	public void reduceHealth(Double h) {
		this.health -= h;
		if (health < 0) {
			System.out.println("Health below 0");
			getParent().addGameEvent(new RemoveGameElementEvent(getParent()));
			if (getParent().hasBehavior(MainCharacter.class)) {
				MainCharacter mc = (MainCharacter) getParent().getBehavior(MainCharacter.class);
				if (mc.getLives()>0) {
					mc.removeLife();
					restoreHealth();
					return;
				}
				else {
					getParent().addGameEvent(new GameOverEvent(getParent()));
				}
				
				
			}
		}
	}
	
	public void restoreHealth() {
		System.out.println("Restore health method ran");
		System.out.println("Default health: " + default_health);
		health = default_health;
	}
	
	@GroovyMethod
	public void decayHealth(Double time) {
		reduceHealth(time*decayRate);
	}
	
	@GroovyMethod
	public Double getHealth() {
		return this.health;
	}
	
	@GroovyMethod
	public boolean isAlive() {
		return (this.health>=0);
	}
	
	@GroovyMethod
	public void revive() {
		health = default_health;
	}
	
}
