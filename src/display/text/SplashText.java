package display.text;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author August Ning
 * Class that creates text for the spash screen
 */
public class SplashText extends Text {

	/**
	 * @param text 		The desired text of the label
	 * @param x_loc 	The X location of the label
	 * @param y_loc 	The Y location of the label
	 * @param textColor	The desired font color of the label
	 * @param fontSize 	The desired font size of the label
	 */
	public SplashText(String text, int x_loc, int y_loc, Color textColor, int fontSize) {
		this.setText(text);
		this.setLayoutX(x_loc);
		this.setLayoutY(y_loc);
		this.setFill(textColor);
		this.setFont(new Font("Helvetica", fontSize));
	}

}
