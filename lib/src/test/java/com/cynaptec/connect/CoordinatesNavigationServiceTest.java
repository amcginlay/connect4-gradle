package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoordinatesNavigationServiceTest {

	CoordinatesNavigationService coordinatesNavigationService;
	Coordinates origin;
	
	@BeforeEach
	public void before() {
		coordinatesNavigationService = new CoordinatesNavigationService();
		origin = new Coordinates(1, 1);
	}	
	
//	EAST		(+1, +0), 
	@Test
	public void test_Move_ReturnsXPlusOne_WhenMovingEast() {
		Coordinates coords = coordinatesNavigationService.move(origin, DirectionEnum.EAST);
		assertEquals(origin.getX() + 1, coords.x);
	}

//	WEST		(-1, +0), 
	@Test
	public void test_Move_ReturnsXMinusOne_WhenMovingWest() {
		Coordinates coords = coordinatesNavigationService.move(origin, DirectionEnum.WEST);
		assertEquals(origin.getX() - 1, coords.x);
	}
//	NORTH		(+0, +1), 
	@Test
	public void test_Move_ReturnsYPlusOne_WhenMovingNorth() {
		Coordinates coords = coordinatesNavigationService.move(origin, DirectionEnum.NORTH);
		assertEquals(origin.getY() + 1, coords.y);
	}
//	SOUTH		(+0, -1), 
	@Test
	public void test_Move_ReturnsYMinusOne_WhenMovingSouth() {
		Coordinates coords = coordinatesNavigationService.move(origin, DirectionEnum.SOUTH);
		assertEquals(origin.getY() - 1, coords.y);
	}
//	NORTHEAST	(+1, +1), 
	@Test
	public void test_Move_ReturnsNorthAndEast_WhenMovingNorthEast() {
		Coordinates actual = coordinatesNavigationService.move(origin, DirectionEnum.NORTHEAST);
		Coordinates expected = coordinatesNavigationService.move(origin, DirectionEnum.NORTH);
		expected = coordinatesNavigationService.move(expected, DirectionEnum.EAST);
		assertEquals(expected, actual);
	}
//	NORTHWEST	(+1, -1),
	@Test
	public void test_Move_ReturnsNorthAndWest_WhenMovingNorthWest() {
		Coordinates actual = coordinatesNavigationService.move(origin, DirectionEnum.NORTHWEST);
		Coordinates expected = coordinatesNavigationService.move(origin, DirectionEnum.NORTH);
		expected = coordinatesNavigationService.move(expected, DirectionEnum.WEST);
		assertEquals(expected, actual);
	}
//	SOUTHEAST	(-1, +1), 
	@Test
	public void test_Move_ReturnsSouthAndEast_WhenMovingSouthEast() {
		Coordinates actual = coordinatesNavigationService.move(origin, DirectionEnum.SOUTHEAST);
		Coordinates expected = coordinatesNavigationService.move(origin, DirectionEnum.SOUTH);
		expected = coordinatesNavigationService.move(expected, DirectionEnum.EAST);
		assertEquals(expected, actual);
	}
//	SOUTHWEST	(-1, -1);
	@Test
	public void test_Move_ReturnsSouthAndWest_WhenMovingSouthWest() {
		Coordinates actual = coordinatesNavigationService.move(origin, DirectionEnum.SOUTHWEST);
		Coordinates expected = coordinatesNavigationService.move(origin, DirectionEnum.SOUTH);
		expected = coordinatesNavigationService.move(expected, DirectionEnum.WEST);
		assertEquals(expected, actual);
	}
	
}
