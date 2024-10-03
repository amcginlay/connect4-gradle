package com.cynaptec.connect;

//import static java.util.Objects.requireNonNull;
import static com.cynaptec.connect.Objects.*;

public class PreviewablePlayingPieceFactory implements IPreviewablePlayingPieceFactory {

	private IPlayingPieceFactory playingPieceFactory;
	private IPlayingPiece next;
	
	public PreviewablePlayingPieceFactory(IPlayingPieceFactory playingPieceFactory) {
		requireNonNull(playingPieceFactory, "playingPieceFactory");
		this.playingPieceFactory = playingPieceFactory;
	}

	public IPlayingPiece createInstance() {
		IPlayingPiece result = null;		
		if (next != null) {
			result = next;
		} else {			
			result = playingPieceFactory.createInstance();
		}
		next = null;
		return result;
	}

	public IPlayingPiece preview() {
		if (next == null) {
			next = playingPieceFactory.createInstance();
		}
		return next;
	}

}
