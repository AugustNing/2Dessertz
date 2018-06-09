package engine.collision;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RadialPolygonalExtrapolator implements ShapeExtrapolator {
	int resolution = 8;
	int sides = 4;
	double offset = 0;

	@Override
	public Shape extrapolateShape(ImageView imageview) {
		Bounds bounds = imageview.getBoundsInLocal();
		Point2D center = new Point2D((bounds.getMinX() + bounds.getMaxX())/2, (bounds.getMinY() + bounds.getMaxY())/2);
		
		Image image = imageview.getImage();
		
		for (double i = offset; i < 360 + offset; i += 360.0/(double) sides) {
			bounds.getMinX() - center.getX(); 
		}
		
		PixelReader px = image.getPixelReader();
		
		for (int i = widthStep/2; i < s.getX(); i += widthStep) {
			for (int j = widthStep/2; j < s.getY(); j += widthStep) {
				if (px.getArgb(i, j)>>24 == 0) {
					empty.setX(i-widthStep/2);
					empty.setY(j-heightStep/2);
					result = Shape.subtract(result, empty);
				}
			}
		}
		return result;
	}

}
