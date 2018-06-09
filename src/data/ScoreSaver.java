package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gamePlayer.highScores.Score;

/**
 * @author August Ning and Edward Zhuang
 * 
 *         Class used for saving all the high scores of a game
 *
 */
public class ScoreSaver {

	private String gameName;
	private String gameLocation;
	private String fileName;
	private static final String baseLocation = "./data/gamedata/games/";
	private static final String fileNameSuffix = "HighScores.txt";

	/**
	 * @param name
	 *            is the name of the folder of the game
	 */
	public ScoreSaver(String name) {
		this.gameName = name;
		this.gameLocation = baseLocation + this.gameName + "/";
		this.fileName = this.gameLocation + fileNameSuffix;
	}

	/**
	 * @param scores
	 *            a list of Scores
	 * 
	 *            It takes the list and writes it to a text file in the top level of
	 *            the game folder Throw IllegalArgumentException if the
	 *            file/filepath can not be found
	 */
	public void saveScores(List<Score> scores) {
		File file = new File(fileName);
		System.out.println("Absolute path:" + file.getAbsolutePath());
		if (file.exists()) {
			file.delete();
		}
		PrintWriter out;
		try {
			out = new PrintWriter(file);
			for (Score score : scores) {
				out.println(score.getPlayerName() + " " + score.getScore());
			}
			out.close();
		} catch (FileNotFoundException e) {
			// dont need to do anything i dont think.
		}

	}

	/**
	 * @return a list of scores read in from the corresponding saved text file of
	 *         scores
	 * 
	 *         Throws an error if there is no saved scores file
	 */
	public List<Score> loadSavedScores() {
		List<Score> loadedScores = new ArrayList<>();
		try {
			File scoresFile = new File(this.fileName);
			Scanner in = new Scanner(scoresFile);

			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] info = line.split(" ");
				Score s = new Score(info[0], Integer.parseInt(info[1]));
				loadedScores.add(s);
			}
			in.close();
		} catch (Exception e) {
			System.out.println("The score file has not been intialized or could not be found");
			System.out.println("maybe it's a new game, and no high scores have been saved?");
			return new ArrayList<>();
		}
		return loadedScores;
	}

}
