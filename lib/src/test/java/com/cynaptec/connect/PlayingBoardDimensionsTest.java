package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cynaptec.connect.PlayingBoardDimensions;

public class PlayingBoardDimensionsTest {
	
	@Test ()
	public void test_Setup_ThrowsConnectException_WhenSizeXLessThanOne() {
		assertThrows(IllegalArgumentException.class, () -> {
			new PlayingBoardDimensions(0, 6);
		});
	}
	
	@Test ()
	public void test_Setup_ThrowsConnectException_WhenSizeYLessThanOne() {
		assertThrows(IllegalArgumentException.class, () -> {
			new PlayingBoardDimensions(7, 0);
		});
	}
	
	public void test_Setup_ReturnsNonNullObject_WhenSizesAreWithinValidRange() {
		PlayingBoardDimensions playingBoardDimensions = new PlayingBoardDimensions(999, 42);
		assertNotNull(playingBoardDimensions);
	}

}
