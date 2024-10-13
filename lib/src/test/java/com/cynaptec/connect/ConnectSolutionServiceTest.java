package com.cynaptec.connect;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectSolutionServiceTest {

	private PlayingBoard playingBoard;
	private CoordinatesNavigationService coordinatesNavigationService;
	private ConnectSolutionService connectSolutionService;
	
	private PlayingBoardSequenceBuilder playingBoardSequenceBuilder;
	private Coordinates origin;
	
	@BeforeEach
	public void before() {
		playingBoard = new PlayingBoard(new ConnectFourPlayingBoardDimensions());
		coordinatesNavigationService = new CoordinatesNavigationService();
		connectSolutionService = new ConnectSolutionService(
				playingBoard, 
				coordinatesNavigationService,
				ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		
		playingBoardSequenceBuilder = new PlayingBoardSequenceBuilder(playingBoard, coordinatesNavigationService);
		origin = new Coordinates(1, 1);
	}
	
	@Test()
	public void test_Constructor_ThrowsANullPointerException_WhenPlayingBoardIsNull() {
		assertThrows(NullPointerException.class, () -> {
			new ConnectSolutionService(null, coordinatesNavigationService, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		});
	}

	@Test()
	public void test_Constructor_ThrowsANullPointerException_WhenCoordinateNavigationServiceIsNull() {
		assertThrows(NullPointerException.class, () -> {
			new ConnectSolutionService(playingBoard, null, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		});
	}

	@Test()
	public void test_Constructor_ThrowsAnIllegalArgumentException_WhenConnectedPiecesToWinLessThanOne() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ConnectSolutionService(playingBoard, coordinatesNavigationService, 0);
		});
	}

	@Test
	public void test_Solve_ReturnNull_WhenPlayingBoardIsEmpty() {
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertNull(solutions);
	}
	
	@Test
	public void test_Solve_ReturnsNull_WhenASinglePieceIsPlayed() {
		playingBoard.setPieceAt(new P1PlayingPiece(), new Coordinates(1,1));
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertNull(solutions);
	}
	
	@Test
	public void test_Solve_ReturnsNull_WhenAHorizontalNoWinIsBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.EAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN - 3);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertNull(solutions);
	}
	
	@Test
	public void test_Solve_ReturnsOneSolutionOfFourElements_WhenAHorizontalWinIsBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.EAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertEquals(1, solutions.size());
		assertEquals(ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN, solutions.get(0).size());
	}
	
	@Test
	public void test_Solve_ReturnsOneSolutionOfFiveElements_WhenALargerHorizontalWinIsBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.EAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN + 1);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertEquals(1, solutions.size());
		assertEquals(ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN + 1, solutions.get(0).size());
	}

	@Test
	public void test_Solve_ReturnsFourElements_WhenAVerticalWinIsBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.NORTH, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertEquals(1, solutions.size());
		assertEquals(ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN, solutions.get(0).size());
	}
	
	@Test
	public void test_Solve_ReturnsOneSolutionOfFourElements_WhenADiagonalWinIsBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		// NOTE at the playing board level, we can set both X and Y (no gravity!)
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.NORTHEAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertEquals(1, solutions.size());
		assertEquals(ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN, solutions.get(0).size());
	}
	
	@Test
	public void test_Solve_ReturnsTwoSolutions_WhenTwoOverlappingWinsAreBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P2PlayingPieceFactory();
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.EAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.NORTH, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertEquals(2, solutions.size());
	}
	
	@Test
	public void test_Solve_ReturnsThreeSolutions_WhenThreeOverlappingWinsIsBuilt() {
		IPlayingPieceFactory playingPieceFactory = new P1PlayingPieceFactory();
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.EAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.NORTH, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		playingBoardSequenceBuilder.build(playingPieceFactory, origin, DirectionEnum.NORTHEAST, ConnectFourPlayingService.CONNECTED_PIECES_TO_WIN);
		List<List<IPlayingPiece>> solutions = connectSolutionService.solve();
		assertEquals(3, solutions.size());
	}
	
//	private void BuildSequence(IPlayingPieceFactory playingPieceFactory, Coordinates from, DirectionEnum direction, int length) {
//		Coordinates coords = from;
//		for (int i = 1; i <= length; i++) {
//			try {
//				playingBoard.setPieceAt(playingPieceFactory.createInstance(), coords);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			coords = coordinatesNavigationService.move(coords, direction);
//		}
//	}

}
