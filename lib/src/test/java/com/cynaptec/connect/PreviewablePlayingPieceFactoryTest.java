package com.cynaptec.connect;

import static org.junit.Assert.*;

import org.junit.Test;

public class PreviewablePlayingPieceFactoryTest {

	@Test
	public void test_Preview_DoesNotReturnNull_WhenCalledOnce() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		IPreviewablePlayingPieceFactory previewablePlayingPieceFactory = new PreviewablePlayingPieceFactory(playingPieceFactory);
		IPlayingPiece playingPiece = previewablePlayingPieceFactory.preview();
		assertNotNull(playingPiece);
	}

	@Test
	public void test_Preview_DoesNotReturnNull_WhenCalledTwice() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		IPreviewablePlayingPieceFactory previewablePlayingPieceFactory = new PreviewablePlayingPieceFactory(playingPieceFactory);
		IPlayingPiece playingPiece2 = previewablePlayingPieceFactory.preview();
		assertNotNull(playingPiece2);
	}
	
	@Test
	public void test_Preview_ReturnsSameObject_WhenCalledTwice() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		IPreviewablePlayingPieceFactory previewablePlayingPieceFactory = new PreviewablePlayingPieceFactory(playingPieceFactory);
		IPlayingPiece playingPiece1 = previewablePlayingPieceFactory.preview();
		IPlayingPiece playingPiece2 = previewablePlayingPieceFactory.preview();
		assertSame(playingPiece1, playingPiece2);
	}

	@Test
	public void test_Preview_ReturnsDifferentObject_WhenCalledAfterCreateInstance() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		IPreviewablePlayingPieceFactory previewablePlayingPieceFactory = new PreviewablePlayingPieceFactory(playingPieceFactory);
		IPlayingPiece playingPiece1 = previewablePlayingPieceFactory.createInstance();
		IPlayingPiece playingPiece2 = previewablePlayingPieceFactory.preview();
		assertNotSame(playingPiece1, playingPiece2);
	}	

	@Test
	public void test_CreateInstance_ReturnsSameObject_WhenCalledAfterPreview() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		IPreviewablePlayingPieceFactory previewablePlayingPieceFactory = new PreviewablePlayingPieceFactory(playingPieceFactory);
		IPlayingPiece playingPiece1 = previewablePlayingPieceFactory.preview();
		IPlayingPiece playingPiece2 = previewablePlayingPieceFactory.createInstance();
		assertSame(playingPiece1, playingPiece2);
	}	
}
