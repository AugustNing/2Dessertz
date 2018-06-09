package gamePlayer.highScores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import data.ScoreSaver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Calvin and Jeff - Peer Programming
 *
 */
public class ConcreteHighScores implements HighScores {

	private List<Score> scores;
	private ScoreSaver scoreSaver;

	private static final String NAMELABEL = "Name";
	private static final String PLAYERNAME = "playerName";
	private static final String SCORELABEL = "Score";
	private static final String SCORENAME = "score";

	private TableView<Score> table;

	public ConcreteHighScores() {
		scores = new ArrayList<Score>();
		table = new TableView<Score>();
		setupTableProperties(970, 50, 235, 265);
	}

	/**
	 * initializes a "ConcreteHighScore" by creating a new priorityqueue for the
	 * scores
	 * 
	 * @param game
	 *            - a string that represents the game currently being played - the
	 *            high score is matched to the specific game.
	 */
	public ConcreteHighScores(String game) {
		scores = new ArrayList<Score>();
		table = new TableView<Score>();
		scoreSaver = new ScoreSaver(game);

		for (Score score : scoreSaver.loadSavedScores()) {
			this.addScore(score.getPlayerName(), score.getScore());
		}
		setupTableProperties(970, 50, 235, 265);
	}

	private void setupTableProperties(double xPos, double yPos, double width, double height) {
		table.setEditable(false);
		table.setLayoutX(xPos);
		table.setLayoutY(yPos);
		table.setMaxWidth(width);
		table.setMaxHeight(height);
		setupTableColumns();
	}

	private void setupTableColumns() {
		table.setEditable(true);

		TableColumn<Score, String> nameCol = new TableColumn(NAMELABEL);
		nameCol.setCellValueFactory(new PropertyValueFactory<Score, String>(PLAYERNAME));
		nameCol.setMaxWidth(150);
		nameCol.setResizable(false);

		TableColumn<Score, Integer> scoreCol = new TableColumn(SCORELABEL);
		scoreCol.setCellValueFactory(new PropertyValueFactory<Score, Integer>(SCORENAME));
		scoreCol.setMinWidth(150);
		scoreCol.setResizable(false);

		table.getColumns().addAll(nameCol, scoreCol);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see GamePlayer.HighScores#addScore(java.lang.String, int)
	 */
	@Override
	public void addScore(String name, int score) {
		Score newScore = new Score(name, score);
		if (scores.size() < 10) {
			scores.add(newScore);
		} else {
			if (scores.get(0).getScore() < score) {
				scores.remove(0);
				scores.add(newScore);
			}
		}
		Collections.sort(scores, new Score.ScoreComparator());
		updateScoreTable();

	}

	/**
	 * Called by gameEngine when player dies
	 * 
	 * @param name
	 * @param score
	 */
	public void addScoreWhenGameOver(String name, int score) {
		this.addScore(name, score);
	}

	/**
	 * updates front end display of scores, and xml file of scores
	 */
	private void updateScoreTable() {

		ObservableList<Score> observableScoreList = FXCollections.observableArrayList(scores);
		Collections.reverse(observableScoreList);
		table.setItems(observableScoreList);
		scoreSaver.saveScores(this.scores);

	}

	/**
	 * method for printing the current high scores for testing
	 */
	public void printQ() {
		int size = scores.size();
		for (int i = 0; i < size; i++) {
			System.out.println(scores.get(i));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see GamePlayer.HighScores#loadScores(java.util.Map)
	 */
	@Override
	public void loadScores(Map<?, ?> scores) {
		// TODO Auto-generated method stub
	}

	public TableView<Score> getScores() {
		return table;
	}

	@Override
	public void clear() {
		scores.clear();
		updateScoreTable();
	}

	public List<Score> getScoreList() {
		return scores;
	}

}
