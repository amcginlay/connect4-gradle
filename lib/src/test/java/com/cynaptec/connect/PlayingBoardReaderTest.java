package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cynaptec.connect.ConnectFourPlayingBoardDimensions;
import com.cynaptec.connect.IPlayingPiece;
import com.cynaptec.connect.PlayingBoard;
import com.cynaptec.connect.PlayingBoardDimensions;

public class PlayingBoardReaderTest {
	
	private PlayingBoard playingBoard;
	private PlayingBoardReader playingBoardReader;
	
	@Before
	public void before() {
		PlayingBoardDimensions playingBoardDimensions = new ConnectFourPlayingBoardDimensions();
		playingBoard = new PlayingBoard(playingBoardDimensions);
		playingBoardReader = new PlayingBoardReader(playingBoard);
	}
	
	@Test (expected=NullPointerException.class)
	public void test_Constructor_ThrowsNullPointerException_WhenPassedNullArgument() {
		new PlayingBoardReader(null);
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

	@Test (expected=NullPointerException.class)
	public void test_GetPieceAt_ThrowsNullPointerException_WhenWhenNoCoordinateProvided() {
		playingBoardReader.getPieceAt(null);
	}	

	@Test (expected=IllegalArgumentException.class)
	public void test_GetPieceAt_ThrowsIllegalArgumentException_WhenAttemptingToInspectAnOutOfBoundsLocationMax() {
		playingBoardReader.getPieceAt(new Coordinates(playingBoardReader.getSizeX() + 1, 1));
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
