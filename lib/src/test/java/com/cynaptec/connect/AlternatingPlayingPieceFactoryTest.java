package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cynaptec.connect.IPlayingPiece;

public class AlternatingPlayingPieceFactoryTest {

	private IPlayingPieceFactory playingPieceFactory;
	
	@Before
	public void before() {
		playingPieceFactory = new AlternatingPlayingPieceFactory(
				new P1PlayingPieceFactory(), 
				new P2PlayingPieceFactory()
			);
	}
	
	@Test
	public void test_CreateInstance_ReturnsP1_WhenConfiguredWithP1ForFirstInvocation() {
		IPlayingPiece playingPiece = playingPieceFactory.createInstance();
		assertSame(P1PlayingPiece.class, playingPiece.getClass());
	}

	@Test
	public void test_CreateInstance_ReturnsInstanceOfFirstFactory_WhenConfiguredWithTwoFactories() {
		IPlayingPieceFactory reversedPlayingPieceFactory = new AlternatingPlayingPieceFactory(
				new P2PlayingPieceFactory(),
				new P1PlayingPieceFactory() 
			);
		IPlayingPiece playingPiece = reversedPlayingPieceFactory.createInstance();
		assertSame(P2PlayingPiece.class, playingPiece.getClass());
	}

	@Test
	public void test_CreateInstance_ReturnTypesDifferBetweenFirstAndSecondInvocations_WhoeverPlaysFirst() {
		IPlayingPiece playingPieceFirst = playingPieceFactory.createInstance();
		IPlayingPiece playingPieceSecond = playingPieceFactory.createInstance();
		assertNotSame(playingPieceFirst.getClass(), playingPieceSecond.getClass()); // assuming same factory type not used twice
	}

	@Test
	public void test_CreateInstance_ReturnTypesSameBetweenFirstAndThirdInvocations_WhoeverPlaysFirst() {
		IPlayingPiece playingPieceFirst = playingPieceFactory.createInstance();
		playingPieceFactory.createInstance(); // second piece discarded
		IPlayingPiece playingPieceThird = playingPieceFactory.createInstance();
		assertSame(playingPieceFirst.getClass(), playingPieceThird.getClass());
	}
}
