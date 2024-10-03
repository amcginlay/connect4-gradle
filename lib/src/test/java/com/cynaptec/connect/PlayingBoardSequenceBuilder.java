package com.cynaptec.connect;

public class PlayingBoardSequenceBuilder {

	private PlayingBoard playingBoard;
	private CoordinatesNavigationService coordinatesNavigationService;

	public PlayingBoardSequenceBuilder(PlayingBoard playingBoard, CoordinatesNavigationService coordinatesNavigationService) {
		this.playingBoard = playingBoard;
		this.coordinatesNavigationService = coordinatesNavigationService;
	}
	
	public void build(IPlayingPieceFactory playingPieceFactory, Coordinates from, DirectionEnum direction, int length) {
		Coordinates coords = from;
		for (int i = 1; i <= length; i++) {
			try {
				playingBoard.setPieceAt(playingPieceFactory.createInstance(), coords);
			} catch (Exception e) {
				e.printStackTrace();
			}
			coords = coordinatesNavigationService.move(coords, direction);
		}
	}
}
