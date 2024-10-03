package com.cynaptec.connect;

public class PlayingBoardDimensions {

	private int sizeX;
	private int sizeY;
	
	public PlayingBoardDimensions(int sizeX, int sizeY) {
		
		if (sizeX < 1 || sizeY < 1)
			throw new IllegalArgumentException();
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

}
