package com.cynaptec.connectconsole;

import java.util.List;

import com.cynaptec.connect.ConnectFourPlayingService;
import com.cynaptec.connect.ConnectFourPlayingServiceFactory;
import com.cynaptec.connect.ConnectFourScoreKeeperService;
import com.cynaptec.connect.Coordinates;
import com.cynaptec.connect.IPlayingPiece;
import com.cynaptec.connect.IllegalMoveException;
import com.cynaptec.connect.P1PlayingPiece;
import com.cynaptec.connect.PlayingBoardReader;

public class ConnectFourTextStreamController {

	private ConnectFourScoreKeeperService scoreKeeper;
	private ConnectFourPlayingServiceFactory modelFactory;
	private ConnectFourTextStreamView view;
	
	public ConnectFourTextStreamController(ConnectFourPlayingServiceFactory modelFactory, ConnectFourTextStreamView view) {
		this.modelFactory = modelFactory;
		this.view = view;
		scoreKeeper = new ConnectFourScoreKeeperService();
	}

	public void main() {
		boolean playAgain = true;
		while (playAgain) {
			ConnectFourPlayingService model = modelFactory.createInstance();
			while (model.getIsInProgress()) {
				displayPlayingBoard(model);
				String currentPlayer = model.getNextPlayerName();
				view.writeLine(currentPlayer + ": Next Move? (1-7 or Q)");
				try {
					String command = view.readLine();
					model.takeTurn(command);
				} catch (IllegalMoveException e) {
					view.writeLine(currentPlayer + ": Illegal move, please try again");
				}
			}
			if (model.getIsGameAborted()) {
				view.writeLine("Game Aborted");
			} else {
				List<List<IPlayingPiece>> winningSequences = model.getWinningSequences();
				displayPlayingBoard(model, winningSequences);
				if (winningSequences == null) {
					view.writeLine("Tie.  Game Over");
				} else {
					IPlayingPiece winner = model.getWinner();
					scoreKeeper.updateScore(winner);
					view.writeLine("Game Over.  " + winner.getTextDescription() + " wins.  " + getScore());
				}
			}
			view.writeLine("New Game? (y or n)");
			String playAgainResponse = view.readLine();
			playAgain = playAgainResponse.toLowerCase().startsWith("y");
		}
	}
	
	private String getScore() {
		if (scoreKeeper.getPlayerOneScore() > scoreKeeper.getPlayerTwoScore()) {
			return String.format("Player One leads %d:%d", scoreKeeper.getPlayerOneScore(), scoreKeeper.getPlayerTwoScore());
		}
		if (scoreKeeper.getPlayerOneScore() < scoreKeeper.getPlayerTwoScore()) {
			return String.format("Player Two leads %d:%d", scoreKeeper.getPlayerTwoScore(), scoreKeeper.getPlayerOneScore());
		}
		return String.format("Scores level %d:%d", scoreKeeper.getPlayerOneScore(), scoreKeeper.getPlayerTwoScore());
	}

	private void displayPlayingBoard(ConnectFourPlayingService model) {
		displayPlayingBoard(model, null);
	}
	
	private void displayPlayingBoard(ConnectFourPlayingService model, List<List<IPlayingPiece>> winningSequences) {
		StringBuilder sb = new StringBuilder();
		PlayingBoardReader playingBoardReader = model.getPlayingBoardReader();
		for (int y = playingBoardReader.getSizeY(); y > 0; y--) { // NOTE cycling y-axis in reverse order so it renders as expected 
			for (int x = 1; x <= playingBoardReader.getSizeX(); x++) {
				Coordinates coords = new Coordinates(x, y);
				IPlayingPiece playingPiece = playingBoardReader.getPieceAt(coords);
				if (playingPiece != null) {
					boolean playingPieceIsInASequence = doesPlayingPieceAppearInAnySequence(winningSequences, playingPiece);
					if (playingPiece instanceof P1PlayingPiece) {
						sb.append(playingPieceIsInASequence ? '0' : 'O');
					} else { // P2PlayingPiece
						sb.append(playingPieceIsInASequence ? '#' : 'X');
					}
				} else {
					sb.append('.');
				}
			}
			sb.append(System.getProperty("line.separator"));
		}
		view.writeLine(sb.toString());
	}

	private boolean doesPlayingPieceAppearInAnySequence(List<List<IPlayingPiece>> sequences, IPlayingPiece playingPiece) {
		if (sequences == null || playingPiece == null) {
			return false; // not found
		}
		
		for (List<IPlayingPiece> sequence : sequences) {
			for (IPlayingPiece sequencePlayingPiece : sequence) {
				if (playingPiece == sequencePlayingPiece) {
					return true; // found
				}
			}
		}
		
		return false; // not found
	}

}
