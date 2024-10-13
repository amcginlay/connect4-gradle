package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class P1PlayingPieceFactoryTest {

	@Test
	public void test_CreateInstance_ReturnsNewP1PlayingPiece_WhenInvoked() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		IPlayingPiece playingPiece = playingPieceFactory.createInstance();
		assertTrue(playingPiece instanceof P1PlayingPiece);
	}
	
	@Test
	public void test_CreateInstance_ReturnsNewP1PlayingPieceWithDescriptionTextPlayerOne_WhenInvoked() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		IPlayingPiece playingPiece = playingPieceFactory.createInstance();
		assertTrue(playingPiece.getTextDescription().equals("Player One"));
	}
}
