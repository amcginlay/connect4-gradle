package com.cynaptec.connect;

public class ConnectFourScoreKeeperService {

	private int playerOneScore;
	private int playerTwoScore;
	
	public ConnectFourScoreKeeperService() {
		playerOneScore = 0;
		playerTwoScore = 0;
	}
	
	public int getPlayerOneScore() {
		return playerOneScore;
	}

	public int getPlayerTwoScore() {
		return playerTwoScore;
	}

	public void updateScore(IPlayingPiece winner) {
		if (winner instanceof P1PlayingPiece) {
			playerOneScore++;
		} else { // P2PlayingPiece
			playerTwoScore++;
		}
	}
}
