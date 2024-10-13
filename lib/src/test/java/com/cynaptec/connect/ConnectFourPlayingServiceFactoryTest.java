package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConnectFourPlayingServiceFactoryTest {

	@Test
	public void test_CreateInstance_ReturnsPlayingServiceWithPlayerOneStartingFirst_WhenInvokedForTheFirstTime() {
		ConnectFourPlayingServiceFactory connectFourPlayingServiceFactory = new ConnectFourPlayingServiceFactory();
		ConnectFourPlayingService ConnectFourPlayingService = connectFourPlayingServiceFactory.createInstance();
		assertEquals("Player One", ConnectFourPlayingService.getNextPlayerName());
	}

	@Test
	public void test_CreateInstance_ReturnsPlayingServiceWithPlayerTwoStartingFirst_WhenInvokedForTheSecondTime() {
		ConnectFourPlayingServiceFactory connectFourPlayingServiceFactory = new ConnectFourPlayingServiceFactory();
		connectFourPlayingServiceFactory.createInstance(); // discard instance
		ConnectFourPlayingService ConnectFourPlayingService = connectFourPlayingServiceFactory.createInstance();
		assertEquals("Player Two", ConnectFourPlayingService.getNextPlayerName());
	}

	@Test
	public void test_CreateInstance_ReturnsPlayingServiceWithPlayerOneStartingFirst_WhenInvokedForTheThirdTime() {
		ConnectFourPlayingServiceFactory connectFourPlayingServiceFactory = new ConnectFourPlayingServiceFactory();
		connectFourPlayingServiceFactory.createInstance(); // discard instance
		connectFourPlayingServiceFactory.createInstance(); // discard instance
		ConnectFourPlayingService ConnectFourPlayingService = connectFourPlayingServiceFactory.createInstance();
		assertEquals("Player One", ConnectFourPlayingService.getNextPlayerName());
	}
}
