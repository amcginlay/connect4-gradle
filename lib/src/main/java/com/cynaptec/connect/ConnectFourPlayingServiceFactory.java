package com.cynaptec.connect;

public class ConnectFourPlayingServiceFactory {

	private boolean p1PlayerStarts;
	
	public ConnectFourPlayingServiceFactory() {
		p1PlayerStarts = false;
	}
	
	public com.cynaptec.connect.ConnectFourPlayingService createInstance() {
		p1PlayerStarts = !p1PlayerStarts; // alternate the starting player upon each invocation
		PlayingBoard playingBoard = ConnectFourPlayingService.createPlayingBoard();
		IPreviewablePlayingPieceFactory playingPieceFactory = ConnectFourPlayingService.createPreviewablePlayingPieceFactory(p1PlayerStarts);
		CoordinatesNavigationService coordinatesNavigationService = new CoordinatesNavigationService();
		ConnectSolutionService connectSolutionService = new ConnectSolutionService(playingBoard, coordinatesNavigationService, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		
		return new ConnectFourPlayingService(playingBoard, playingPieceFactory, connectSolutionService);
	}

}
