package com.cynaptec.connect;

import static com.cynaptec.connect.Objects.requireNonNull;

public class PlayingBoardReader {

	private PlayingBoard playingBoard;
	
	public PlayingBoardReader(PlayingBoard playingBoard) {
		requireNonNull(playingBoard, "playingBoard");
		this.playingBoard = playingBoard;
	}

	public int getSizeX() {
		return playingBoard.getSizeX();
	}

	public int getSizeY() {
		return playingBoard.getSizeY();
	}
	
	public IPlayingPiece getPieceAt(Coordinates coords) {
		return playingBoard.getPieceAt(coords);
	}

}
