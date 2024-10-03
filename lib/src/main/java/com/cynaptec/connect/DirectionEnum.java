package com.cynaptec.connect;

public enum DirectionEnum {
	// X=EtoW
	// Y=NtoS
	EAST		(+1, +0), 
	WEST		(-1, +0), 
	NORTH		(+0, +1), 
	SOUTH		(+0, -1), 
	NORTHEAST	(+1, +1), 
	NORTHWEST	(-1, +1),
	SOUTHEAST	(+1, -1), 
	SOUTHWEST	(-1, -1);
	
	private int incrementX;
	private int incrementY;

	DirectionEnum(int incrementX, int incrementY) {
		this.incrementX = incrementX;
		this.incrementY = incrementY;
	}

	public int getIncrementX() {
		return incrementX;
	}

	public int getIncrementY() {
		return incrementY;
	}

	public DirectionEnum getOpposite() {
		switch(this) {
			case EAST: return DirectionEnum.WEST;
			case WEST: return DirectionEnum.EAST;
			case NORTH: return DirectionEnum.SOUTH;
			case SOUTH: return DirectionEnum.NORTH;
			case NORTHEAST: return DirectionEnum.SOUTHWEST;
			case NORTHWEST: return DirectionEnum.SOUTHEAST;
			case SOUTHEAST: return DirectionEnum.NORTHWEST;
			case SOUTHWEST: return DirectionEnum.NORTHEAST;
			default: throw new IllegalStateException();
		}
	}
}

