package engine.events.elementevents;

import java.awt.Point;

public class MouseInputEvent extends ElementEvent {
	
	Point mouseLocation;
	
	public MouseInputEvent(Point p) {
		this.mouseLocation = p;
	}
	
	public MouseInputEvent(double x, double y) {
		mouseLocation = new Point();
		this.mouseLocation.setLocation(x, y);
	}
	
	public double getMouseX() {
		return mouseLocation.getX();
	}
	
	public double getMouseY() {
		return mouseLocation.getY();
	}

	public Point getMouseLocation() {
		return mouseLocation;
	}
	
	@Override
	public String toString() {
		return "Mouse Input Event: " + "xpos(" + getMouseX() + ") " + "ypos(" + getMouseY() + " ) location clicked";
	}

	@Override
	public boolean matchesEvent(ElementEvent other) {
		if (other instanceof MouseInputEvent) {
			return (getMouseX() == ((MouseInputEvent)(other)).getMouseX() && 
					getMouseY() == ((MouseInputEvent)(other)).getMouseY());
		}
		return false;
	}
	
	@Override
	public String getTriggerString() {
		return "";
	}
	
	
}
