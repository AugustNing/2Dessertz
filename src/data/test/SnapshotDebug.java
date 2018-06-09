package data.test;

import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;

import data.ImageManager;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SnapshotDebug extends Application {

	public SnapshotDebug() {
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		ImageManager im = new ImageManager("default");

		Pane pane = new Pane();
		pane.setPrefSize(2000, 2000);
		ImageView image1 = new ImageView(im.getImage("bowser.png"));
		ImageView image2 = new ImageView(im.getImage("brick1.png"));
		ImageView image3 = new ImageView(im.getImage("brick2.png"));

		image1.setLayoutX(200);
		image1.setLayoutY(1000);
		image1.setPreserveRatio(true);
		image1.setFitWidth(200);

		image2.setLayoutX(500);
		image2.setLayoutY(700);
		image2.setPreserveRatio(true);
		image2.setFitWidth(200);

		
		image3.setLayoutX(1300);
		image3.setLayoutY(600);
		image3.setPreserveRatio(true);
		image3.setFitWidth(200);


		pane.getChildren().add(image1);
		pane.getChildren().add(image2);
		pane.getChildren().add(image3);
		
		Group root = new Group();
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 400, 400);
		
		stage.setScene(scene);
		stage.show();
				
		 WritableImage wi = new WritableImage((int) pane.getPrefWidth(), (int) pane.getPrefHeight());
		 pane.snapshot(new SnapshotParameters(), wi);

		    try {
		    	File f = new File("./data/gamedata/games/Demo308/background.png");
		    	RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
		        ImageIO.write(ri, "png", f);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}
