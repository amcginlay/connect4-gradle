package com.cynaptec.connect;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConnectSolutionService {
	
	private PlayingBoard playingBoard;
	private CoordinatesNavigationService coordinatesNavigationService;
	private int connectedPiecesToWin;
	
	public ConnectSolutionService(
			PlayingBoard playingBoard, 
			CoordinatesNavigationService coordinatesNavigationService,
			int connectedPiecesToWin) {
		requireNonNull(playingBoard, "playingBoard");
		requireNonNull(coordinatesNavigationService, "coordinateNavigationService");

		if (connectedPiecesToWin <= 0)
			throw new IllegalArgumentException();
		
		this.playingBoard = playingBoard;
		this.coordinatesNavigationService = coordinatesNavigationService;
		this.connectedPiecesToWin = connectedPiecesToWin;
	}

	public List<List<IPlayingPiece>> solve() {
		List<List<IPlayingPiece>> results = null;
		for (int x = 1; x <= playingBoard.getSizeX(); x++) {
			for (int y = 1; y <= playingBoard.getSizeY(); y++) {
				// based upon (x,y) potentially representing the end-point of a solution,
				// navigate in each of our eight directions on the board to discover if a solution exists
				for (DirectionEnum direction : DirectionEnum.values()) {
					List<IPlayingPiece> foundSolution = solve(new Coordinates(x, y), direction);					
					if (foundSolution != null) {
						// sort all solutions so we can check for duplicates
						Collections.sort(foundSolution, new Comparator<IPlayingPiece>() 
	                    {
		                     public int compare(IPlayingPiece p1, IPlayingPiece p2) 
		                     {
		                    	 return p1.hashCode() - p2.hashCode();
		                     }
	                    });
												
						// initialise results, if not done so already
						if (results == null) {
							results = new ArrayList<List<IPlayingPiece>>();
						}
						
						// solutions will be reported in both directions
						// if this one's not a dupe then add to the results but keep looking, there could be more ...
						if (!results.contains(foundSolution)) {
							results.add(foundSolution);
						}
					}
				}
			}
		}

		return results;
		
	}

	private List<IPlayingPiece> solve(Coordinates coords, DirectionEnum direction) {
		// only endpoints can form the start of a solution so discard all others
		if (!isEndpoint(coords, direction)) {
			return null;
		}
		
		// build the sequence beginning with the endpoint
		IPlayingPiece firstPiece = playingBoard.getPieceAt(coords);		
		if (firstPiece == null) {
			return null;
		}
		
		List<IPlayingPiece> results = new ArrayList<IPlayingPiece>();
		results.add(firstPiece);
		
		while(true) {
			// find next coords in chosen direction
			coords = coordinatesNavigationService.move(coords, direction);
			// if it's out of bounds abort while loop
			if (!playingBoard.isInBounds(coords)) {
				break;
			}
			
			// if no piece at coords abort while loop 
			IPlayingPiece nextPiece = playingBoard.getPieceAt(coords);
			if (nextPiece == null) {
				break;
			}
			
			// if no type match for this piece abort while loop
			if (nextPiece.getClass() != firstPiece.getClass()) {
				break;
			}
			
			// if we got here, add the found piece to results and resume the while loop
			results.add(nextPiece);
		}
		
		if (results.size() < connectedPiecesToWin) {
			results = null; // a sequence was found but it's not big enough to win so discard it
		}
		
		return results;
	}

	private boolean isEndpoint(Coordinates coords, DirectionEnum direction) {
		// It's important to know if any coords is an endpoint so we can 
		// be sure we're counting the sequence length accurately
		// NOTE it's possible to be a sequence of one and still be an endpoint
				
		// if coordinate out of bounds it cannot be an endpoint
		if (!playingBoard.isInBounds(coords)) {
			return false;
		}
		
		// if no piece at coordinate it cannot be an endpoint
		IPlayingPiece piece = playingBoard.getPieceAt(coords);
		if (piece == null) {
			return false;
		}
		
		// piece exists - if opposite neighbour coordinate out of bounds it must be an endpoint
		Coordinates oppositeNeighbourCoord = coordinatesNavigationService.move(coords, direction.getOpposite());
		if (!playingBoard.isInBounds(oppositeNeighbourCoord)) {
			return true;
		}		
		
		// if opposite neighbour is NULL it must be an endpoint
		IPlayingPiece oppositeNeighbourPiece = playingBoard.getPieceAt(oppositeNeighbourCoord);
		if (oppositeNeighbourPiece == null) {
			return true;
		}
		
		// opposite neighbour exists - if types match it cannot be considered an endpoint in this direction
		if (oppositeNeighbourPiece.getClass() == piece.getClass()) {
			return false;
		}
		
		// otherwise, types must differ and it is an endpoint
		return true;
	}
}
