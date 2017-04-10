package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.Board.CellContent;

import java.util.Optional;

public class Rule implements GPSRule{
    
	private CellContent piece;
    private int x;
    private int y;
    private Integer cost;

    public Rule(CellContent piece, int x, int y, final Integer cost) {
        this.piece = piece;
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    @Override
    public Integer getCost() {
        return cost;
    }

    @Override
    public String getName() {
        return piece.toString() + " (" + x + ", " + y + ")";
    }

    @Override
    public Optional<GPSState> evalRule(GPSState gpsState) {
        Board board = ((Board) gpsState).copy();

        if (board.getPiece(x,y) != CellContent.EMPTY)
            return Optional.empty();

        board.setPiece(x,y,piece);

        if (!board.validateMove(x,y))
            return Optional.empty();

        board.updateValues(x,y);

        return Optional.of(board);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
