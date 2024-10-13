package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CoordinatesTest {

	@Test
	public void test_GetX_ReturnsOne_WhenConstructedWithOneTwo() {
		Coordinates coords = new Coordinates(1, 2);
		assertEquals(1, coords.getX());
	}

	@Test
	public void test_GetY_ReturnsTwo_WhenConstructedWithOneTwo() {
		Coordinates coords = new Coordinates(1, 2);
		assertEquals(2, coords.getY());
	}

	@Test
	public void test_Equals_ReturnsTruw_WhenTwoIdenticalCoordinatesConstructed() {
		Coordinates coordsOne = new Coordinates(1, 2);
		Coordinates coordsTwo = new Coordinates(1, 2);
		assertEquals(coordsOne, coordsTwo);
	}
}
