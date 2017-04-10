package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class NeighboursHeuristic implements Heuristic{

    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        Integer n = board.getSize();
        Integer accum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(board.getPiece(i,j) == Board.CellContent.EMPTY) {
                    if (board.getPiece(i - 1, j) != null || board.getPiece(i - 1, j) != Board.CellContent.EMPTY)
                        accum++;
                    if (board.getPiece(i + 1, j) != null || board.getPiece(i + 1, j) != Board.CellContent.EMPTY)
                        accum++;
                    if (board.getPiece(i, j - 1) != null || board.getPiece(i, j - 1) != Board.CellContent.EMPTY)
                        accum++;
                    if (board.getPiece(i, j + 1) != null || board.getPiece(i, j + 1) != Board.CellContent.EMPTY)
                        accum++;
                }
            }
        }
        return accum;
    }

    @Override
    public Integer getCost() {
        return 4;
    }

    @Override
    public String getName() {
        return "Neighbours";
    }
}
