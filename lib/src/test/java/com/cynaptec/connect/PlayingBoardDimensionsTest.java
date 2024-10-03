package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cynaptec.connect.PlayingBoardDimensions;

public class PlayingBoardDimensionsTest {
	
	@Test (expected=IllegalArgumentException.class)
	public void test_Setup_ThrowsConnectException_WhenSizeXLessThanOne() {
		new PlayingBoardDimensions(0, 6);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void test_Setup_ThrowsConnectException_WhenSizeYLessThanOne() {
		new PlayingBoardDimensions(7, 0);
	}
	
	public void test_Setup_ReturnsNonNullObject_WhenSizesAreWithinValidRange() {
		PlayingBoardDimensions playingBoardDimensions = new PlayingBoardDimensions(999, 42);
		assertNotNull(playingBoardDimensions);
	}

}
