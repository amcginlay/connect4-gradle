package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectFourPlayingServiceTest {

	private PlayingBoard playingBoard;
	private IPreviewablePlayingPieceFactory previewablePlayingPieceFactory;
	private CoordinatesNavigationService coordinatesNavigationService;
	private ConnectSolutionService connectSolutionService;
	private ConnectFourPlayingService connectFourPlayingService;

	@BeforeEach
	public void before() {
		playingBoard = ConnectFourPlayingService.createPlayingBoard();
		previewablePlayingPieceFactory = ConnectFourPlayingService.createPreviewablePlayingPieceFactory(true);
		coordinatesNavigationService = new CoordinatesNavigationService();
		connectSolutionService = new ConnectSolutionService(playingBoard, coordinatesNavigationService, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		connectFourPlayingService = new ConnectFourPlayingService(
			playingBoard, 
			previewablePlayingPieceFactory,
			connectSolutionService
		);
	}

	@Test()
	public void test_Constructor_ThrowsANullPointerException_WhenPlayingBoardIsNull() {
		assertThrows(NullPointerException.class, () -> {
			new ConnectFourPlayingService(null, previewablePlayingPieceFactory, connectSolutionService);
		});
	}

	@Test()
	public void test_Constructor_ThrowsANullPointerException_WhenPlayingPieceFactoryIsNull() {
		assertThrows(NullPointerException.class, () -> {
			new ConnectFourPlayingService(playingBoard, null, connectSolutionService);
		});
	}

	@Test()
	public void test_Constructor_ThrowsANullPointerException_WhenConnectSolutionServiceIsNull() {
		assertThrows(NullPointerException.class, () -> {
			new ConnectFourPlayingService(playingBoard, previewablePlayingPieceFactory, null);
		});
	}

	public void test_GetNextPlayerName_ReturnsPlayerOne_WhenGameIsStarted() throws IllegalMoveException {
		String nextPlayer = connectFourPlayingService.getNextPlayerName();
		connectFourPlayingService.takeTurn("q");
		assertEquals("Player One", nextPlayer);
	}	
	
	@Test()
	public void test_TakeTurn_ThrowsAnIllegalMoveException_WhenStringInputNotRecognised() throws IllegalMoveException {
		assertThrows(IllegalMoveException.class, () -> {
			connectFourPlayingService.takeTurn("illegal argument");
		});
	}
	
	public void test_TakeTurn_GameEntersAbortedState_WhenGameIsQuit() throws IllegalMoveException {
		connectFourPlayingService.takeTurn("q");
		assertTrue(connectFourPlayingService.getIsGameAborted());
	}
	
	@Test()
	public void test_TakeTurn_ThrowsAnIllegalMoveException_WhenGameIsAborted() throws IllegalMoveException {
		assertThrows(IllegalMoveException.class, () -> {
			connectFourPlayingService.takeTurn("q");
			connectFourPlayingService.takeTurn(1);
		});
	}
	
	@Test()
	public void test_TakeTurn_ThrowsAnIllegalMoveException_WhenXIsLessThanOne() throws IllegalMoveException {
		assertThrows(IllegalMoveException.class, () -> {
			connectFourPlayingService.takeTurn(0);
		});
	}
	
	@Test()
	public void test_TakeTurn_ThrowsAnIllegalMoveException_WhenXIsGreaterThanConnectFourSize() throws IllegalMoveException {
		assertThrows(IllegalMoveException.class, () -> {
			connectFourPlayingService.takeTurn(new ConnectFourPlayingBoardDimensions().getSizeX() + 1);
		});
	}
	
	@Test()
	public void test_TakeTurn_ThrowsAnIllegalMoveConnectException_WhenColumnIsFull() throws IllegalMoveException {
		assertThrows(IllegalMoveException.class, () -> {
			int yMax = new ConnectFourPlayingBoardDimensions().getSizeY();
			// fill the column ...
			for (int y = 1; y <= yMax; y++) {
				connectFourPlayingService.takeTurn(1);
			}
			// ... one more should cause an exception
			connectFourPlayingService.takeTurn(1);
		});
	}
	
	@Test
	public void test_TakeTurn_WillPlaceItemInRowOne_WhenAddedToEmptyColumn() throws IllegalMoveException {
		IPlayingPiece preview = previewablePlayingPieceFactory.preview();
		connectFourPlayingService.takeTurn(1);
		IPlayingPiece actual = playingBoard.getPieceAt(new Coordinates(1, 1));
		assertSame(preview, actual);
	}
	
	@Test
	public void test_TakeTurn_WillPlaceSecondItemAboveFirst_WhenAddedToSameColumn() throws IllegalMoveException {
		connectFourPlayingService.takeTurn(1);
		IPlayingPiece expected = previewablePlayingPieceFactory.preview();
		connectFourPlayingService.takeTurn(1);
		IPlayingPiece actual = playingBoard.getPieceAt(new Coordinates(1, 2));
		assertSame(expected, actual);
	}
	

	@Test()
	public void test_TakeTurn_WillThrowIllegalMoveException_WhenGameIsOver() throws IllegalMoveException {
		assertThrows(IllegalMoveException.class, () -> {
			fillPlayingBoardToSimulateTiedMatch();
			connectFourPlayingService.takeTurn(1);
		});
	}
	
	@Test
	public void test_GetIsGameOver_ReturnsFalse_WhenNewGameStarted() throws IllegalMoveException {
		assertFalse(connectFourPlayingService.getIsGameOver());
	}
	
	@Test
	public void test_GetWinningSequences_ReturnsNull_WhenNewGameStarted() throws IllegalMoveException {
		assertNull(connectFourPlayingService.getWinningSequences());
	}
	
	@Test
	public void test_GetWinningSequences_ReturnsNull_WhenAWinnerAlmostExists() throws IllegalMoveException {
		connectFourPlayingService.takeTurn(3);
		connectFourPlayingService.takeTurn(4);
		connectFourPlayingService.takeTurn(3);
		connectFourPlayingService.takeTurn(4);
		connectFourPlayingService.takeTurn(3);
		connectFourPlayingService.takeTurn(4);
		// NOTE no winner at this point, each player has only taken three turns
		assertNull(connectFourPlayingService.getWinningSequences());
	}
	
	@Test
	public void test_GetWinningSequences_ReturnsNonNull_WhenAWinnerExists() throws IllegalMoveException {
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(1); // this sequence should result in player one vertical winner in column 1
		assertNotNull(connectFourPlayingService.getWinningSequences());
	}
	
	@Test
	public void test_GetWinner_ReturnsPlayerOnePieceClass_WhenPlayerOneIsTheWinner() throws IllegalMoveException {
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(1); // this sequence should result in player one vertical winner in column 1
				
		IPlayingPiece pieceFromWinningSequence = connectFourPlayingService.getWinner();
		assertNotNull(pieceFromWinningSequence);
		assertSame(P1PlayingPiece.class, pieceFromWinningSequence.getClass()); 
	}
	
	@Test
	public void test_GetIsGameOver_ReturnsTrue_WhenAWinnerExists() throws IllegalMoveException {
		connectFourPlayingService.takeTurn(1);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(2);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(3);
		connectFourPlayingService.takeTurn(7);
		connectFourPlayingService.takeTurn(4); // this sequence should result in player one horizontal winner in row 1
		assertTrue(connectFourPlayingService.getIsGameOver());
	}
	
	@Test
	public void test_GetIsGameOver_ReturnsTrue_WhenPlayingBoardIsFull() throws IllegalMoveException {
		fillPlayingBoardToSimulateTiedMatch();
		assertEquals(true, connectFourPlayingService.getIsGameOver());
	}
	
	@Test
	public void test_GetIsGameOver_ReturnsFalse_WhenWinnerIsNullAndBoardIsNotFull() throws IllegalMoveException {
		connectFourPlayingService.takeTurn(1); // can't win OR fill the board in one move
		assertFalse(connectFourPlayingService.getIsGameOver());
	}

	@Test
	public void test_GetPlayingBoardReader_ReturnsNotNull_WhenInvoked() {
		PlayingBoardReader playingBoardReader = connectFourPlayingService.getPlayingBoardReader();
		assertNotNull(playingBoardReader);
	}

	private void fillPlayingBoardToSimulateTiedMatch() throws IllegalMoveException {
		String tiedPlayingSequence = "171717 717171 626262 262626 353535 535353 444444";
		char[] turns = tiedPlayingSequence.replaceAll(" ", "").toCharArray();
		for (int index = 0; index < turns.length; index++) {
			connectFourPlayingService.takeTurn(Character.toString(turns[index]));
		}
	}
	
	@Test
	public void test_GetIsGameOver_ReturnsTrue_WhenAWinnerExistsFromARealGameExample() throws IllegalMoveException {
		// TODO refactor this code into a helper method
		String tiedPlayingSequence = "12 43 32 21 17 1";
		char[] turns = tiedPlayingSequence.replaceAll(" ", "").toCharArray();
		for (int index = 0; index < turns.length; index++) {
			connectFourPlayingService.takeTurn(Character.toString(turns[index]));
		}
		assertTrue(connectFourPlayingService.getIsGameOver());
	}
	
}
