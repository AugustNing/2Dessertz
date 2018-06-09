package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class DataDemo {

	public static void main(String[] args) throws IOException {

		String descriptionImage = "./data/images/rcd.jpg";
		File imageFile = new File(descriptionImage);
		BufferedImage image = ImageIO.read(imageFile);
		
//		GameInitializer gameInitializer = new GameInitializer("Demo308");
//		
//		GameSaver gameSaver = new GameSaver("Demo308");
//		gameSaver.addDescription("Demo308Game", "This is for demo purposes", image);
//		
//		ModelGameState model = new ModelGameState();
//		GameState testState = model.getState();
//		
//		gameSaver.saveGameState(testState);
		
//		ImageManager im = new ImageManager("default");
//		im.getAllImages();

//		String gamepath = "Demo308";
//		
//		ScoreSaver ss = new ScoreSaver(gamepath);
//		
//		List<Score> scores = new ArrayList<>();
//		
//		scores.add(new Score("August", 200));
//		scores.add(new Score("Eddie", 100));
//		scores.add(new Score("Calvin", 300));
//		scores.add(new Score("Jeff", 211));
//		scores.add(new Score("Martin", 330));
//		scores.add(new Score("Trishul", 110));
//		scores.add(new Score("Maddie", 540));
//		scores.add(new Score("Summer", 130));
//		
//		ss.saveScores(scores);
//		System.out.println("lol fuck");
//		
//		List<Score> readScores = ss.loadSavedScores();
//		for (Score s : readScores) {
//			System.out.println(s);
//		}
		
//		XStream x = new XStream();
		ImageManager im = new ImageManager("default");
//		SceneBackground sb = new SceneBackground(1000,1000);
////		sb.addImage(im.getImage("bowser.png"));
////		sb.addImage(im.getImage("brick1.png"));
//		
//		String saved = x.toXML(sb);
//		System.out.println(saved);
		
//		Pane pane = new Pane();
//		pane.setPrefSize(2000, 2000);
//		ImageView image1 = new ImageView(im.getImage("bowser.png"));
//		ImageView image2 = new ImageView(im.getImage("brick1.png"));
//		ImageView image3 = new ImageView(im.getImage("brick2.png"));
//
//		image1.setLayoutX(100);
//		image1.setLayoutY(1000);
//
//		image2.setLayoutX(500);
//		image2.setLayoutY(700);
//		
//		image3.setLayoutX(1300);
//		image3.setLayoutY(600);
//		
//		pane.getChildren().add(image1);
//		pane.getChildren().add(image2);
//		pane.getChildren().add(image3);
//			System.out.println(pane.getPrefHeight());
//		 WritableImage wi = new WritableImage((int) pane.getPrefWidth(), (int) pane.getPrefHeight());
//		 pane.snapshot(new SnapshotParameters(), wi);
//
//		    try {
//		    	File f = new File("./data/gamedata/games/Demo308/background.png");
//		    	RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
//		        ImageIO.write(ri, "png", f);
//		    } catch (Exception e) {
//		    	e.printStackTrace();
//		    }

		
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "hello");
		map.put(2, "bye");
		
		System.out.println(map.containsKey(null));
	}
}
