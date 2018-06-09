package engine.behaviors;

import authoring.groovy.GroovyMethod;
import engine.GameElement;

public class ExitPortal extends Behavior{
	private Integer portalID;
	
	public ExitPortal(GameElement ge, Integer portalID) {
		super(ge);
		this.portalID = portalID;
	}
	
	public ExitPortal(GameElement ge) {
		this(ge, 1);
	}
	
	@GroovyMethod
	public Integer getPortalID() {
		return portalID; 
	}

}
