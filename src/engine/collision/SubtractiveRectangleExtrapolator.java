package engine.collision;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SubtractiveRectangleExtrapolator implements ShapeExtrapolator {
	int resolution = 2;

	@Override
	public Shape extrapolateShape(Image image, double x, double y, double width, double height) {
		Rectangle s = new Rectangle(x, y, width, height);
		Shape result = new Rectangle(x, y, width, height);
		
		int imageWidthStep = (int) image.getWidth()/resolution;
		int imageHeightStep = (int) image.getHeight()/resolution;
		
		int shapeWidthStep = (int) s.getWidth()/resolution;
		int shapeHeightStep = (int) s.getHeight()/resolution;
		
		Rectangle empty = new Rectangle(x, y, shapeWidthStep, shapeHeightStep);
		
		PixelReader px = image.getPixelReader();
		
		for (int i = 0; i < resolution; i++) {
			for (int j = 0; j < resolution; j++) {
				if (px.getArgb((i*imageWidthStep + imageWidthStep/2), (j*imageHeightStep + imageHeightStep/2))>>24 == 0) {
					empty.setX(x + i*shapeWidthStep);
					empty.setY(y + j*shapeHeightStep);
					
					result = Shape.subtract(result, empty);
				}
			}
		}
		return result;
	}
	
}
