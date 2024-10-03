package com.cynaptec.connect;

//import static java.util.Objects.requireNonNull;
import static com.cynaptec.connect.Objects.*;

import java.util.List;

public class ConnectFourPlayingService {

	private enum GameStateEnum {
		IN_PROGRESS,
		OVER,
		ABORTED
	}
	
	public static final int CONNECTED_PIECES_TO_WIN = 4;

	private PlayingBoard playingBoard;
	private IPreviewablePlayingPieceFactory playingPieceFactory;
	private ConnectSolutionService connectSolutionService;
	private GameStateEnum gameState; // NOTE use getter and setter to access this private member variable

	public static PlayingBoard createPlayingBoard() {
		return new PlayingBoard(new ConnectFourPlayingBoardDimensions());
	}

	public static IPreviewablePlayingPieceFactory createPreviewablePlayingPieceFactory(boolean p1PlayerStarts) {
		IPlayingPieceFactory p1PlayingPieceFactory = new P1PlayingPieceFactory();
		IPlayingPieceFactory p2PlayingPieceFactory = new P2PlayingPieceFactory();
		IPlayingPieceFactory alternatingPlayingPieceFactory = new AlternatingPlayingPieceFactory(
				p1PlayerStarts ? p1PlayingPieceFactory : p2PlayingPieceFactory,
				p1PlayerStarts ? p2PlayingPieceFactory : p1PlayingPieceFactory);
		return new PreviewablePlayingPieceFactory(alternatingPlayingPieceFactory);
	}

	public ConnectFourPlayingService(
			PlayingBoard playingBoard, 
			IPreviewablePlayingPieceFactory playingPieceFactory,
			ConnectSolutionService connectSolutionService) {
		requireNonNull(playingBoard, "playingBoard");
		requireNonNull(playingPieceFactory, "playingPieceFactory");
		requireNonNull(connectSolutionService, "connectSolutionService");
		this.playingBoard = playingBoard;
		this.playingPieceFactory = playingPieceFactory;
		this.connectSolutionService = connectSolutionService;
		initialiseGameState();
	}

	private void initialiseGameState() {
		gameState = GameStateEnum.IN_PROGRESS;
	}
	
	private GameStateEnum getGameState() {
		if (gameState == GameStateEnum.IN_PROGRESS) {
			// if game is IN_PROGRESS, check if game is OVER
			if (isPlayingBoardFull()) {
				gameState = GameStateEnum.OVER;
			}
			List<List<IPlayingPiece>> winningSequences = getWinningSequences();
			if (winningSequences != null) {
				gameState = GameStateEnum.OVER;
			}
		}
		return gameState;
	}
	
	private void setGameState(GameStateEnum gameState) {
		// OVER and ABORTED are end-states, so we reject any 
		// attempt to modify the game state unless it's IN_PROGRESS
		if (this.gameState != GameStateEnum.IN_PROGRESS)
			throw new IllegalArgumentException();
		
		this.gameState = gameState;
	}
	
	public String getNextPlayerName() {
		return playingPieceFactory.preview().getTextDescription();
	}

	public void takeTurn(String stringMove) throws IllegalMoveException {
		if (stringMove == null) {
			throw new IllegalArgumentException();
		}

		if (stringMove.trim().toLowerCase().equals("q")) {
			// abort
			setGameState(GameStateEnum.ABORTED);
			return;
		}
			
		try {  
			int intMove = Integer.parseInt(stringMove);
			takeTurn(intMove);
		} catch (NumberFormatException e) {
			throw new IllegalMoveException();
     	}   
	}

	public void takeTurn(int x) throws IllegalMoveException {
		if (getGameState() != GameStateEnum.IN_PROGRESS)
			throw new IllegalMoveException();
		
		if (x < 1 || x > playingBoard.getSizeX())
			throw new IllegalMoveException();

		int piecesInColumnX = getColumnUsageCount(x);
		if (piecesInColumnX >= playingBoard.getSizeY())
			throw new IllegalMoveException();

		int y = piecesInColumnX + 1;
		takeTurn(new Coordinates(x, y));
	}
	
	public boolean getIsInProgress() {
		return (getGameState() == GameStateEnum.IN_PROGRESS);
	}
	public boolean getIsGameOver() {
		return (getGameState() == GameStateEnum.OVER);
	}

	public boolean getIsGameAborted() {
		return (getGameState() == GameStateEnum.ABORTED);
	}

	public List<List<IPlayingPiece>> getWinningSequences() {
		return connectSolutionService.solve();
	}

	public PlayingBoardReader getPlayingBoardReader() {
		return new PlayingBoardReader(playingBoard);
	}

	private void takeTurn(Coordinates coords) {
		// NOTE public overloaded method elsewhere
		IPlayingPiece playingPiece = playingPieceFactory.createInstance();
		playingBoard.setPieceAt(playingPiece, coords);
	}

	private int getColumnUsageCount(int x) {
		int result = 0;
		for (int y = 1; y <= playingBoard.getSizeY(); y++) {
			if (playingBoard.getPieceAt(new Coordinates(x, y)) != null) {
				result++;
			}
		}
		return result;
	}

	private boolean isPlayingBoardFull() {
		for (int y = 1; y <= playingBoard.getSizeY(); y++) {
			for (int x = 1; x <= playingBoard.getSizeX(); x++) {
				if (playingBoard.getPieceAt(new Coordinates(x, y)) == null) {
					return false;
				}
			}
		}
		return true;
	}

	public IPlayingPiece getWinner() {
		List<List<IPlayingPiece>> winningSequences = getWinningSequences();
		if (winningSequences == null) return null;			
		if (winningSequences.size() == 0) return null;			
		if (winningSequences.get(0).size() == 0) return null;			
		return getWinningSequences().get(0).get(0);
	}
}
