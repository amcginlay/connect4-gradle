package com.cynaptec.connect;

public class P2PlayingPieceFactory implements IPlayingPieceFactory {

	public IPlayingPiece createInstance() {
		return new P2PlayingPiece();
	}

}
