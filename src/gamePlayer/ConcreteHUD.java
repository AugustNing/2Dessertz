package gamePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ConcreteHUD extends Pane implements HUD {

	private String gameName;
	private Map<String, Object> info;
	private int xLabel = 0;
	private int yLabel = 0;
	private List<Label> labelList;
	private static final int fontSize = 20;

	public ConcreteHUD(String name) {
		gameName = name;
		info = new HashMap<>();
		labelList = new ArrayList<>();
		setupPane();
	}

	/**
	 * setting up front end JavaFX of the pane
	 */
	//
	private void setupPane() {
		this.setLayoutX(30);
		this.setLayoutY(30);
		this.setWidth(800);
		this.setHeight(650);

		setupHUDText();
	}

	private void setupHUDText() {
		yLabel = 0;
		xLabel = 0;
		this.getChildren().removeAll(labelList);
		labelList.clear();
		setUpName();

		for (String label : info.keySet()) {

			Label text = new Label(label + ": " + info.get(label).toString());
			// text = new Label("hi");

			text.setTextFill(Color.WHITE);
			text.setLayoutX(xLabel);
			text.setLayoutY(yLabel);
			text.setFont(new Font(fontSize));
			this.getChildren().add(text);
			labelList.add(text);
			xLabel = (int) (xLabel + text.getText().length() * 12);

			if ((xLabel + 100) > 900) {
				xLabel = 0;
				yLabel = yLabel + 25;
			}

		}
	}

	private void setUpName() {
		Label nameText = new Label(gameName);
		nameText.setTextFill(Color.WHITE);
		nameText.setLayoutX(xLabel);
		nameText.setLayoutY(yLabel - 25);
		nameText.setFont(new Font(fontSize));
		this.getChildren().add(nameText);
	}

	@Override
	public void updateInfo(Map<String, Object> info) {
		this.info = info;
		setupHUDText();
	}

}
