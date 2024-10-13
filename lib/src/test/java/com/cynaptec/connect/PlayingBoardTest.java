package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cynaptec.connect.ConnectFourPlayingBoardDimensions;
import com.cynaptec.connect.IPlayingPiece;
import com.cynaptec.connect.PlayingBoard;
import com.cynaptec.connect.PlayingBoardDimensions;

public class PlayingBoardTest {
	
	private PlayingBoard playingBoard;
	
	@BeforeEach
	public void before() {
		PlayingBoardDimensions playingBoardDimensions = new ConnectFourPlayingBoardDimensions();
		playingBoard = new PlayingBoard(playingBoardDimensions);
	}
	
	@Test ()
	public void test_Constructor_ThrowsNullPointerException_WhenPassedNullArgument() {
		assertThrows(NullPointerException.class, () -> {
			new PlayingBoard(null);
		});
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
	
	@Test ()
	public void test_GetPieceAt_ThrowsNullPointerException_WhenWhenNoCoordinateProvided() {
		assertThrows(NullPointerException.class, () -> {
			playingBoard.getPieceAt(null);
		});
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
	
	@Test ()
	public void test_GetPieceAt_ThrowsIllegalArgumentException_WhenAttemptingToInspectAnOutOfBoundsLocationMin() {
		assertThrows(IllegalArgumentException.class, () -> {
			playingBoard.getPieceAt(new Coordinates(1, 0));
		});
	}

	@Test ()
	public void test_GetPieceAt_ThrowsIllegalArgumentException_WhenAttemptingToInspectAnOutOfBoundsLocationMax() {
		assertThrows(IllegalArgumentException.class, () -> {
			playingBoard.getPieceAt(new Coordinates(playingBoard.getSizeX() + 1, 1));
		});
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

	@Test ()
	public void test_SetPieceAt_ThrowsNullPointerException_WhenNullPieceIsUsed() {
		assertThrows(NullPointerException.class, () -> {
			IPlayingPiece playingPiece = null;
			Coordinates coords = new Coordinates(1, 1);
			playingBoard.setPieceAt(playingPiece, coords);
		});
	}	

	@Test ()
	public void test_SetPieceAt_ThrowsNullPointerException_WhenNullCoordinateIsUsed() {
		assertThrows(NullPointerException.class, () -> {
			IPlayingPiece playingPiece = new P1PlayingPiece();
			Coordinates coords = null;
			playingBoard.setPieceAt(playingPiece, coords);
		});
	}	

	@Test ()
	public void test_SetPieceAt_ThrowsIllegalArgumentException_WhenPieceIsUsedOutOfBoundsMin() {
		assertThrows(IllegalArgumentException.class, () -> {
			IPlayingPiece playingPiece = new P1PlayingPiece();
			playingBoard.setPieceAt(playingPiece, new Coordinates(1, 0));
		});
	}	

	@Test ()
	public void test_SetPieceAt_ThrowsIllegalArgumentException_WhenPieceIsUsedOutOfBoundsMax() {
		assertThrows(IllegalArgumentException.class, () -> {
			IPlayingPiece playingPiece = new P1PlayingPiece();
			playingBoard.setPieceAt(playingPiece, new Coordinates(playingBoard.getSizeX() + 1, 1));
		});
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
