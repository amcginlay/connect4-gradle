package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Test;

public class P2PlayingPieceFactoryTest {

	@Test
	public void test_CreateInstance_ReturnsNewP2PlayingPiece_WhenInvoked() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		IPlayingPiece playingPiece = playingPieceFactory.createInstance();
		assertTrue(playingPiece instanceof P2PlayingPiece);
	}

	@Test
	public void test_CreateInstance_ReturnsNewP2PlayingPieceWithDescriptionTextPlayerTwo_WhenInvoked() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		IPlayingPiece playingPiece = playingPieceFactory.createInstance();
		assertTrue(playingPiece.getTextDescription().equals("Player Two"));
	}
	
}
