package com.cynaptec.connect;

public class AlternatingPlayingPieceFactory 
	implements IPlayingPieceFactory {
	
	private IPlayingPieceFactory factoryOne;
	private IPlayingPieceFactory factoryTwo;
	private boolean isFactoryOneCreatingNext;
	
	public AlternatingPlayingPieceFactory(IPlayingPieceFactory factoryOne, IPlayingPieceFactory factoryTwo) {
		this.factoryOne = factoryOne;
		this.factoryTwo = factoryTwo;
		isFactoryOneCreatingNext = true;
	}

	public IPlayingPiece createInstance() {
		IPlayingPiece result = null;
		if (isFactoryOneCreatingNext) {
			result = factoryOne.createInstance();
		} else {
			result = factoryTwo.createInstance();
		}
		isFactoryOneCreatingNext = !isFactoryOneCreatingNext;
		return result;
	}
	
}
