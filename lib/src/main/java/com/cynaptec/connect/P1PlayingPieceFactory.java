package com.cynaptec.connect;

public class P1PlayingPieceFactory implements IPlayingPieceFactory {

	public IPlayingPiece createInstance() {
		return new P1PlayingPiece();
	}

}
