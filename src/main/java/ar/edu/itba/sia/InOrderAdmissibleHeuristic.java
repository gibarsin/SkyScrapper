package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class InOrderAdmissibleHeuristic implements Heuristic{
    private Integer size;

    public InOrderAdmissibleHeuristic(Board initialBoard) {
        this.size = initialBoard.getSize();
    }

    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        Integer n = board.getSize();
        Integer accum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Board.CellContent piece = board.getPiece(i,j);
                if (piece == Board.CellContent.EMPTY)
                    accum += 2 * size - i - j;
            }
        }
        return accum;
    }

    @Override
    public Integer getCost() {
        return 2*size;
    }

    @Override
    public String getName() {
        return "In Order";
    }
}
