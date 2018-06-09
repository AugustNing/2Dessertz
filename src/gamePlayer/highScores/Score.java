package gamePlayer.highScores;

import java.util.Comparator;

public class Score {

	private String playerName;
	private int score;

	public Score(String name, int score) {
		playerName = name;
		this.score = score;
	}
	public String toString() {
		return playerName +": " + score;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setPlayerName(String newplayerName) {
		playerName = newplayerName;

	}

	public void setScore(int newscore) {
		score = newscore;

	}
	


	public static class ScoreComparator implements Comparator<Score> {

		@Override
		public int compare(Score score1, Score score2) {
			if (score1.score > score2.score) {
				return 1;
			}
			if (score1.score < score2.score) {
				return -1;
			}
			return 0;
		}
		

	}
}
