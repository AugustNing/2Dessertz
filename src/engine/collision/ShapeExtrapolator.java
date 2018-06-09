package engine.collision;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

/**
 * @author Martin
 * 
 * Extract a close-to-exact shape of an image.
 *
 */
public interface ShapeExtrapolator {
	public Shape extrapolateShape(Image image, double x, double y, double width, double height);

}
