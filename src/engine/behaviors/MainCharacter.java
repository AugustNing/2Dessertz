package engine.behaviors;

import java.util.Arrays;
import java.util.List;

import authoring.groovy.GroovyMethod;
import engine.GameElement;

public class MainCharacter extends Behavior{
	
	private Integer gamelives;
	private Boolean xscroll = false;
	private Boolean yscroll = false;
	
	public MainCharacter(GameElement ge, int gamelives, boolean xscroll, boolean yscroll) {
		super(ge);
		this.xscroll = xscroll;
		this.yscroll = yscroll;
		this.gamelives = gamelives;
	}
	
	public MainCharacter(GameElement ge) {
		super(ge);
		gamelives = 1;
	}
	
	@GroovyMethod
	public int getLives() {
		return gamelives;
	}
	
	@GroovyMethod
	public int removeLife() {
		return gamelives -= gamelives;
	}
	
	@GroovyMethod
	public boolean isXscroll() {
		return xscroll;
	}
	
	@GroovyMethod
	public boolean isYScroll() {
		return yscroll;
	}	
	
	public List<Double> getImageViewOffset(Double width, Double height) {
		List<Double> offset = Arrays.asList(0.0,0.0);
		MandatoryBehavior mc_mandatory = (MandatoryBehavior) getParent().getBehavior(MandatoryBehavior.class);
		Double mc_width_os = mc_mandatory.getShape().getBoundsInLocal().getWidth()/2;
		Double mc_height_os = mc_mandatory.getShape().getBoundsInLocal().getHeight()/2;
		if (xscroll) {
			offset.set(0, width/2 - mc_mandatory.getX() - mc_width_os);
		}
		if (yscroll) {
			offset.set(1, height/2 - mc_mandatory.getY() - mc_height_os);
		}
		return offset;
	}
	
	@Override
	protected void addRequiredBehaviors() {
		addBehaviorsIfNotExisting(Arrays.asList(MovableCharacter.class, Killable.class, TimeTracker.class));
	}


}
