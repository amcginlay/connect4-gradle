package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectFourScoreKeeperServiceTest {

	@Test
	public void test_GetPlayerOneScore_ReturnsZeroAfterInitialisation() {
		ConnectFourScoreKeeperService connectFourScoreKeeperService = new ConnectFourScoreKeeperService();
		int playerOneScore = connectFourScoreKeeperService.getPlayerOneScore();
		assertEquals(0, playerOneScore);
	}

	@Test
	public void test_GetPlayerTwoScore_ReturnsZeroAfterInitialisation() {
		ConnectFourScoreKeeperService connectFourScoreKeeperService = new ConnectFourScoreKeeperService();
		int playerTwoScore = connectFourScoreKeeperService.getPlayerTwoScore();
		assertEquals(0, playerTwoScore);
	}

	@Test
	public void test_GetPlayerOneScore_ReturnsOneOnceIncremented() {
		ConnectFourScoreKeeperService connectFourScoreKeeperService = new ConnectFourScoreKeeperService();
		connectFourScoreKeeperService.updateScore(new P1PlayingPiece());
		int playerOneScore = connectFourScoreKeeperService.getPlayerOneScore();
		assertEquals(1, playerOneScore);
	}

	@Test
	public void test_GetPlayerTwoScore_ReturnsTwoOnceIncrementedTwice() {
		ConnectFourScoreKeeperService connectFourScoreKeeperService = new ConnectFourScoreKeeperService();
		connectFourScoreKeeperService.updateScore(new P2PlayingPiece());
		connectFourScoreKeeperService.updateScore(new P2PlayingPiece());
		int playerTwoScore = connectFourScoreKeeperService.getPlayerTwoScore();
		assertEquals(2, playerTwoScore);
	}
}
