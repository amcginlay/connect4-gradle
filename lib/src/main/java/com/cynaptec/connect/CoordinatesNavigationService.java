package com.cynaptec.connect;

public class CoordinatesNavigationService {
	public Coordinates move(Coordinates from, DirectionEnum direction) {
		return new Coordinates(from.getX() + direction.getIncrementX(), from.getY() + direction.getIncrementY());
	}

}
