package ar.edu.itba.sia;

import ar.edu.itba.sia.Board.CellContent;
import ar.edu.itba.sia.gps.api.*;


import java.util.Optional;

public class SimpleRule implements GPSRule {

	private final CellContent ruleType;

	public Optional<GPSState> evalRule(GPSState gpsState) {
		Board board = ((Board)gpsState).copy();
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				if (board.getPiece(i, j) == CellContent.EMPTY) {
					board.setPiece(i, j, this.ruleType);
					return board.isValid()? Optional.of(board) : Optional.empty();
				}
			}
		}
		return Optional.empty();
	}

	public Integer getCost() { return 5;}

	public SimpleRule(CellContent ruleType) {
		this.ruleType = ruleType;
	}

	public String getName() {
		return this.ruleType.toString().substring(0, 1) + this.ruleType.toString().toLowerCase().substring(1) + " Rule";
	}

	@Override
	public String toString() {return getName(); }
}
