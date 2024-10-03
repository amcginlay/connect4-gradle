package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cynaptec.connect.ConnectFourPlayingBoardDimensions;
import com.cynaptec.connect.IPlayingPiece;
import com.cynaptec.connect.PlayingBoard;
import com.cynaptec.connect.PlayingBoardDimensions;

public class PlayingBoardTest {
	
	private PlayingBoard playingBoard;
	
	@Before
	public void before() {
		PlayingBoardDimensions playingBoardDimensions = new ConnectFourPlayingBoardDimensions();
		playingBoard = new PlayingBoard(playingBoardDimensions);
	}
	
	@Test (expected=NullPointerException.class)
	public void test_Constructor_ThrowsNullPointerException_WhenPassedNullArgument() {
		new PlayingBoard(null);
	}
	
	@Test
	public void test_GetSizeX_ReturnsCorrectValue_WhenConfiguredForConnectFour() {
		int x = playingBoard.getSizeX();
		assertEquals(7, x);
	}
	
	@Test
	public void test_GetSizeY_ReturnsCorrectValue_WhenConfiguredForConnectFour() {
		int y = playingBoard.getSizeY();
		assertEquals(6, y);
	}
	
	@Test (expected=NullPointerException.class)
	public void test_GetPieceAt_ThrowsNullPointerException_WhenWhenNoCoordinateProvided() {
		playingBoard.getPieceAt(null);
	}	

	public void test_IsInBounds_ReturnsFalse_WhenQueryingAnOutOfBoundsLocationMin() {
		playingBoard.isInBounds(new Coordinates(1, 0));
	}	

	public void test_IsInBounds_ReturnsFalse_WhenQueryingAnOutOfBoundsLocationMax() {
		playingBoard.isInBounds(new Coordinates(playingBoard.getSizeX() + 1, 1));
	}	
	
	public void test_IsInBounds_ReturnsTrue_WhenQueryingTheOrigin() {
		playingBoard.isInBounds(new Coordinates(playingBoard.getSizeX() + 1, 1));
	}	
	
	@Test (expected=IllegalArgumentException.class)
	public void test_GetPieceAt_ThrowsIllegalArgumentException_WhenAttemptingToInspectAnOutOfBoundsLocationMin() {
		playingBoard.getPieceAt(new Coordinates(1, 0));
	}	

	@Test (expected=IllegalArgumentException.class)
	public void test_GetPieceAt_ThrowsIllegalArgumentException_WhenAttemptingToInspectAnOutOfBoundsLocationMax() {
		playingBoard.getPieceAt(new Coordinates(playingBoard.getSizeX() + 1, 1));
	}	

	@Test
	public void test_GetPieceAt_ReturnsNullAtOrigin_WhenBoardIsUnplayed() {
		IPlayingPiece playingPiece = playingBoard.getPieceAt(new Coordinates(1, 1));
		assertNull(playingPiece);
	}	

	@Test
	public void test_GetPieceAt_ReturnsNullAtExtremity_WhenBoardIsUnplayed() {
		IPlayingPiece playingPiece = playingBoard.getPieceAt(new Coordinates(playingBoard.getSizeX(), playingBoard.getSizeY()));
		assertNull(playingPiece);
	}	

	@Test (expected=NullPointerException.class)
	public void test_SetPieceAt_ThrowsNullPointerException_WhenNullPieceIsUsed() {
		IPlayingPiece playingPiece = null;
		Coordinates coords = new Coordinates(1, 1);
		playingBoard.setPieceAt(playingPiece, coords);
	}	

	@Test (expected=NullPointerException.class)
	public void test_SetPieceAt_ThrowsNullPointerException_WhenNullCoordinateIsUsed() {
		IPlayingPiece playingPiece = new P1PlayingPiece();
		Coordinates coords = null;
		playingBoard.setPieceAt(playingPiece, coords);
	}	

	@Test (expected=IllegalArgumentException.class)
	public void test_SetPieceAt_ThrowsIllegalArgumentException_WhenPieceIsUsedOutOfBoundsMin() {
		IPlayingPiece playingPiece = new P1PlayingPiece();
		playingBoard.setPieceAt(playingPiece, new Coordinates(1, 0));
	}	

	@Test (expected=IllegalArgumentException.class)
	public void test_SetPieceAt_ThrowsIllegalArgumentException_WhenPieceIsUsedOutOfBoundsMax() {
		IPlayingPiece playingPiece = new P1PlayingPiece();
		playingBoard.setPieceAt(playingPiece, new Coordinates(playingBoard.getSizeX() + 1, 1));
	}	

	public void test_SetPieceAt_DoesNotThrowAnException_WhenPieceIsUsedInBounds() {
		IPlayingPiece playingPiece = new P1PlayingPiece();
		playingBoard.setPieceAt(playingPiece, new Coordinates(1, 1));
		assertTrue(true);
	}	

	@Test
	public void test_SetPieceAt_ReturnsSamePiece_WhenBoardIsUsedOnceAndWeSeekUsedLocation() {
		IPlayingPiece playingPieceToSet = new P1PlayingPiece();
		playingBoard.setPieceAt(playingPieceToSet, new Coordinates(1, 1));
		IPlayingPiece playingPieceToGet = playingBoard.getPieceAt(new Coordinates(1, 1));
		assertSame(playingPieceToSet, playingPieceToGet);
	}
	
	@Test
	public void test_SetPieceAt_ReturnsDifferentPiece_WhenBoardIsUsedOnceAndWeSeekElsewhere() {
		IPlayingPiece playingPieceToSet = new P1PlayingPiece();
		playingBoard.setPieceAt(playingPieceToSet, new Coordinates(1, 1));
		IPlayingPiece playingPieceToGet = playingBoard.getPieceAt(new Coordinates(2, 1));
		assertNotSame(playingPieceToSet, playingPieceToGet);
	}
	
}
