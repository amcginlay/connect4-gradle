package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cynaptec.connect.ConnectFourPlayingBoardDimensions;
import com.cynaptec.connect.IPlayingPiece;
import com.cynaptec.connect.PlayingBoard;
import com.cynaptec.connect.PlayingBoardDimensions;

public class PlayingBoardReaderTest {
	
	private PlayingBoard playingBoard;
	private PlayingBoardReader playingBoardReader;
	
	@BeforeEach
	public void before() {
		PlayingBoardDimensions playingBoardDimensions = new ConnectFourPlayingBoardDimensions();
		playingBoard = new PlayingBoard(playingBoardDimensions);
		playingBoardReader = new PlayingBoardReader(playingBoard);
	}
	
	@Test ()
	public void test_Constructor_ThrowsNullPointerException_WhenPassedNullArgument() {
		assertThrows(NullPointerException.class, () -> {
			new PlayingBoardReader(null);
		});
	}
	
	@Test
	public void test_GetSizeX_ReturnsCorrectValue_WhenConfiguredForConnectFour() {
		int xOriginal = playingBoard.getSizeX();
		int xReader = playingBoardReader.getSizeX();
		assertEquals(xOriginal, xReader);
	}
	
	@Test
	public void test_GetSizeY_ReturnsCorrectValue_WhenConfiguredForConnectFour() {
		int yOriginal = playingBoard.getSizeY();
		int yReader = playingBoardReader.getSizeY();
		assertEquals(yOriginal, yReader);
	}

	@Test ()
	public void test_GetPieceAt_ThrowsNullPointerException_WhenWhenNoCoordinateProvided() {
		assertThrows(NullPointerException.class, () -> {
			playingBoardReader.getPieceAt(null);
		});
	}	

	@Test ()
	public void test_GetPieceAt_ThrowsIllegalArgumentException_WhenAttemptingToInspectAnOutOfBoundsLocationMax() {
		assertThrows(IllegalArgumentException.class, () -> {
			playingBoardReader.getPieceAt(new Coordinates(playingBoardReader.getSizeX() + 1, 1));
		});
	}	

	@Test
	public void test_GetPieceAt_ReturnsNullAtOrigin_WhenBoardIsUnplayed() {
		IPlayingPiece playingPiece = playingBoardReader.getPieceAt(new Coordinates(1, 1));
		assertNull(playingPiece);
	}	

	@Test
	public void test_GetPieceAt_ReturnsNullAtExtremity_WhenBoardIsUnplayed() {
		IPlayingPiece playingPiece = playingBoardReader.getPieceAt(new Coordinates(playingBoardReader.getSizeX(), playingBoardReader.getSizeY()));
		assertNull(playingPiece);
	}	
	
}
