package authoring.display;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;


/**
 * Could be used to isolate button making from level panel class.
 * @author edward zhuang
 *
 */
public class LevelPanelButtonMaker {
	
	List<Button> buttonList;
	
	public LevelPanelButtonMaker() {
		
		buttonList = new ArrayList<>();
		makeButtons();
		
		
	}
	
	public List<Button> getButtonList(){
		
		
		return buttonList;
	}
	
	private void makeButtons() {
		
	}
	
}
